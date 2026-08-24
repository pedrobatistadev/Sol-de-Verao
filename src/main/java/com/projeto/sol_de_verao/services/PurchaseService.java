package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.PurchaseController;
import com.projeto.sol_de_verao.dto.ProductPurchaseDTO;
import com.projeto.sol_de_verao.dto.ProductQuantityDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductPurchaseCreateDTO;
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
public class PurchaseService {
    Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private PurchaseRepository repositoryPurchase;

    @Autowired
    private CustomerRepository repositoryCustomer;

    @Autowired
    private EmployeeRepository repositoryEmployee;

    @Autowired
    private ProductPurchaseRepository repositoryProductPurchase;

    @Autowired
    private AccountsPayableRepository repositoryAccountsPayable;

    @Autowired
    private ProductRepository repositoryProduct;

    @Autowired
    public PagedResourcesAssembler assembler;

    @Transactional
    public ProductPurchaseDTO create(ProductPurchaseCreateDTO productPurchaseCreateDTO) {
        logger.warn("Creating Purchase");

        Customer customer = repositoryCustomer.findById(productPurchaseCreateDTO.getCustomer()).orElseThrow(() -> new EntityNotFoundException("ID field of Customer not found"));
        if (!customer.getEnabled() || customer.getType() != TypeCustomer.SUPPLIER) {
            throw new IllegalStateException("This Customer is disabled or has the wrong type.");
        }

        Employee employee = repositoryEmployee.findById(productPurchaseCreateDTO.getEmployee()).orElseThrow(() -> new EntityNotFoundException("ID field of Employee not found"));
        if (!employee.getEnabled()) {
            throw new IllegalStateException("This Employee is disabled.");
        }

        Purchase purchase = repositoryPurchase.save(new Purchase(customer,employee,productPurchaseCreateDTO.getPaymentMethod().getPaymentMethod(),productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments(),new Date()));

        Double sum = 0.0;
        Double totalValue = 0.0;

        for (ProductQuantityDTO products : productPurchaseCreateDTO.getProductQuantity()) {
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

            repositoryProductPurchase.save(new ProductPurchase(purchase,product,products.getQuantity(),totalValue));
        }

        if (productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments() == 1) {
            repositoryAccountsPayable.save(new AccountsPayable(purchase,1,sum, LocalDate.now().plusMonths(1), new Date()));
        } else {
            if (customer.getCredit()) {
                Double installment = sum / productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments();
                for (int i = 1; i <= productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments(); i++) {
                    repositoryAccountsPayable.save(new AccountsPayable(purchase, i, installment, LocalDate.now().plusMonths(i), new Date()));
                }
            } else {
                throw new IllegalStateException("This customer cannot pay in installments.");
            }
        }

        return new ProductPurchaseDTO(purchase.getId(), ObjectMapper.parseObject(customer, CustomerDTORes.class),
                ObjectMapper.parseObject(employee, EmployeeDTORes.class),productPurchaseCreateDTO.getPaymentMethod(),
                productPurchaseCreateDTO.getProductQuantity(),sum, new Date());
    }

    public PagedModel<EntityModel<ProductPurchaseDTO>> findAll(Pageable pageable) {

        Page<Purchase> purchase = repositoryPurchase.findAll(pageable);

        Page<ProductPurchaseDTO> productPurchaseDTOS = purchase.map((x) -> {
            ProductPurchaseDTO dto = ObjectMapper.parseObject(x, ProductPurchaseDTO.class);
            Hateoas(dto);
            return dto;
        });

        return assembler.toModel(productPurchaseDTOS);
    }

    public ProductPurchaseDTO findById(Long id) {
        logger.warn("Finding purchase");

        ProductPurchaseDTO purchase = ObjectMapper.parseObject(repositoryPurchase.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found")),ProductPurchaseDTO.class);

        Hateoas(purchase);

        return purchase;
    }

    public void delete(Long id) {
        logger.warn("Deleting purchase");

        Purchase purchase = repositoryPurchase.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of purchase not found"));

        try {
            repositoryPurchase.delete(purchase);
        } catch (EntityNotFoundException e) {
            throw new DataIntegrityViolationException("This action is not possible because this purchase is currently in use.");
        }
    }

    private void Hateoas(ProductPurchaseDTO productPurchaseDTO) {
        productPurchaseDTO.add(linkTo(methodOn(PurchaseController.class).create(ObjectMapper.parseObject(productPurchaseDTO, ProductPurchaseCreateDTO.class)))
                .withRel("create").withType("POST"));
        productPurchaseDTO.add(linkTo(methodOn(PurchaseController.class).findById(productPurchaseDTO.getId()))
                .withSelfRel().withType(" GET"));
        productPurchaseDTO.add(linkTo(methodOn(PurchaseController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        productPurchaseDTO.add(linkTo(methodOn(PurchaseController.class).delete(productPurchaseDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
