package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.PurchaseController;
import com.projeto.sol_de_verao.controllers.SaleController;
import com.projeto.sol_de_verao.dto.ProductPurchaseDTO;
import com.projeto.sol_de_verao.dto.ProductQuantityDTO;
import com.projeto.sol_de_verao.dto.ProductSaleDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.*;
import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import com.projeto.sol_de_verao.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class SaleService {

    Logger logger = LoggerFactory.getLogger(SaleService.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private SaleRepository repositorySale;

    @Autowired
    private CustomerRepository repositoryCustomer;

    @Autowired
    private EmployeeRepository repositoryEmployee;

    @Autowired
    private ProductSaleRepository repositoryProductSale;

    @Autowired
    private AccountsReceivableRepository repositoryAccountsReceivable;

    @Autowired ProductRepository repositoryProduct;

    @Autowired
    public PagedResourcesAssembler assembler;

    @Transactional
    public ProductSaleDTO create(ProductSaleCreateDTO productSaleCreateDTO) {
        logger.warn("Creating Sale");

        Customer customer = repositoryCustomer.findById(productSaleCreateDTO.getCustomer()).orElseThrow(() -> new EntityNotFoundException("ID field of Customer not found"));
        if (!customer.getEnabled() || customer.getType() != TypeCustomer.CUSTOMER) {
            throw new IllegalStateException("This Customer is disabled or has the wrong type.");
        }

        Employee employee = repositoryEmployee.findById(productSaleCreateDTO.getEmployee()).orElseThrow(() -> new EntityNotFoundException("ID field of Employee not found"));
        if (!employee.getEnabled()) {
            throw new IllegalStateException("This Employee is disabled.");
        }

        Sale sale = repositorySale.save(new Sale(customer,employee,productSaleCreateDTO.getPaymentMethod().getPaymentMethod(),productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(),new Date()));

        Double sum = 0.0;
        Double totalValue = 0.0;

        for (ProductQuantityDTO products : productSaleCreateDTO.getProductQuantity()) {
            Product product = repositoryProduct.findById(products.getProduct()).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found"));
            if (!product.getEnabled() || product.getQuantity() <= 0) {
                throw new IllegalStateException("This Product is disabled or out of stock.");
            } else if(product.getQuantity() < products.getQuantity()) {
                throw new IllegalStateException("Insufficient product stock !");
            }

            product.setQuantity(product.getQuantity() - products.getQuantity());
            repositoryProduct.save(product);

            Double unitPrice = product.getUnitPrice();
            totalValue = products.getQuantity() * unitPrice;
            sum += totalValue;

            repositoryProductSale.save(new ProductSale(sale,product,products.getQuantity(),totalValue));
        }

        if (productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments() == 1) {
            repositoryAccountsReceivable.save(new AccountsReceivable(sale,1,sum, LocalDate.now().plusMonths(1), new Date()));
        } else {
            if (customer.getCredit()) {
                Double installment = sum / productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments();
                for (int i = 1; i <= productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(); i++) {
                    repositoryAccountsReceivable.save(new AccountsReceivable(sale, i, installment, LocalDate.now().plusMonths(i), new Date()));
                }
            } else {
                throw new IllegalStateException("This customer cannot pay in installments.");
            }
        }

        return new ProductSaleDTO(sale.getId(), ObjectMapper.parseObject(customer, CustomerDTORes.class),
                ObjectMapper.parseObject(employee, EmployeeDTORes.class),productSaleCreateDTO.getPaymentMethod(),
                productSaleCreateDTO.getProductQuantity(),sum, new Date());
    }

    public ProductSaleDTO findById(Long id) {
        logger.warn("Finding Sale");

        ProductSaleDTO purchase = ObjectMapper.parseObject(repositorySale.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found")),ProductSaleDTO.class);

        Hateoas(purchase);

        return purchase;
    }

    public PagedModel<EntityModel<ProductSaleDTO>> findAll(Pageable pageable) {

        Page<Sale> purchase = repositorySale.findAll(pageable);

        Page<ProductSaleDTO> productSaleDTOS = purchase.map((x) -> {
            ProductSaleDTO dto = ObjectMapper.parseObject(x, ProductSaleDTO.class);
            Hateoas(dto);
            return dto;
        });

        return assembler.toModel(productSaleDTOS);
    }

    public void delete(Long id) {
        logger.warn("Deleting Sale");

        Sale sale = repositorySale.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of Sale not found"));

        try {
            repositorySale.delete(sale);
        } catch (EntityNotFoundException e) {
            throw new DataIntegrityViolationException("This action is not possible because this sale is currently in use.");
        }
    }

    private void Hateoas(ProductSaleDTO productSaleDTO) {
        productSaleDTO.add(linkTo(methodOn(SaleController.class).create(ObjectMapper.parseObject(productSaleDTO, ProductSaleCreateDTO.class)))
                .withRel("create").withType("POST"));
        productSaleDTO.add(linkTo(methodOn(SaleController.class).findById(productSaleDTO.getId()))
                .withSelfRel().withType(" GET"));
        productSaleDTO.add(linkTo(methodOn(SaleController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        productSaleDTO.add(linkTo(methodOn(SaleController.class).delete(productSaleDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
