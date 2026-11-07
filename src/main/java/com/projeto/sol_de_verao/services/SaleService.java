package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.SaleController;
import com.projeto.sol_de_verao.dto.ProductQuantitySaleDTO;
import com.projeto.sol_de_verao.dto.ProductSaleDTO;
import com.projeto.sol_de_verao.dto.SaleDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
import com.projeto.sol_de_verao.exception.CreditNotAvailableException;
import com.projeto.sol_de_verao.exception.DatabaseIntegrationException;
import com.projeto.sol_de_verao.exception.InvalidPaymentMethodException;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.*;
import com.projeto.sol_de_verao.model.enums.PaymentMethod;
import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import com.projeto.sol_de_verao.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private PagedResourcesAssembler assembler;

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

        Sale sale = repositorySale.save(new Sale(
                customer,
                employee,
                productSaleCreateDTO.getPaymentMethod().getPaymentMethod(),
                productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(),
                new Date()));

        BigDecimal sum = BigDecimal.ZERO;
        PaymentMethod paymentMethod = productSaleCreateDTO.getPaymentMethod().getPaymentMethod();

        for (ProductQuantitySaleDTO products : productSaleCreateDTO.getProductQuantity()) {
            Product product = repositoryProduct.findById(products.getProduct()).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found"));
            if (!product.getEnabled() || product.getQuantity() <= 0) {
                throw new IllegalStateException("This Product is disabled or out of stock.");
            } else if(product.getQuantity() < products.getQuantity()) {
                throw new IllegalStateException("Insufficient product stock !");
            }

            product.setQuantity(product.getQuantity() - products.getQuantity());
            repositoryProduct.save(product);

            BigDecimal unitPrice = product.getUnitPrice();
            BigDecimal totalValue = unitPrice.multiply(BigDecimal.valueOf(products.getQuantity()));
            sum = sum.add(totalValue);

            repositoryProductSale.save(new ProductSale(sale,product,products.getQuantity(),totalValue));
        }

        if (paymentMethod.equals(PaymentMethod.CASH) ||
                paymentMethod.equals(PaymentMethod.PIX) ||
                paymentMethod.equals(PaymentMethod.DEBIT_CARD)) {

            if (productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments() != 1) {
                throw new InvalidPaymentMethodException("Cash, PIX and debit card payments cannot be divided into installments.");
            }

            repositoryAccountsReceivable.save(new AccountsReceivable(sale,1,sum,new Date()));

        } else if (paymentMethod.equals(PaymentMethod.INSTALLMENTS) ||
                paymentMethod.equals(PaymentMethod.CREDIT_CARD)){

            if (!customer.getCredit()) {
                throw new CreditNotAvailableException("Customer does not have credit available");
            }

            BigDecimal installment = sum.divide(BigDecimal.valueOf(productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments()),2, RoundingMode.DOWN);
            for (int i = 1; i <= productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(); i++) {
                repositoryAccountsReceivable.save(new AccountsReceivable(sale,i,installment,LocalDate.now().plusMonths(i),new Date()));
            }
        }

        return new ProductSaleDTO(sale.getId(),
                ObjectMapper.parseObject(customer, CustomerDTORes.class),
                ObjectMapper.parseObject(employee, EmployeeDTORes.class),
                productSaleCreateDTO.getPaymentMethod(),
                productSaleCreateDTO.getProductQuantity(),
                sum,
                new Date());
    }

    public SaleDTO findById(Long id) {
        logger.warn("Finding Sale");

        Sale sale = repositorySale.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of Sale not found"));

        SaleDTO saleDTO = ObjectMapper.parseObject(sale, SaleDTO.class);

        Hateoas(saleDTO);

        return saleDTO;
    }

    public PagedModel<EntityModel<SaleDTO>> findAll(Pageable pageable) {
        logger.warn("Finding All Sale");

        Page<Sale> sale = repositorySale.findAll(pageable);

        Page<SaleDTO> productSaleDTOS = sale.map((sales) -> {
            SaleDTO dto = ObjectMapper.parseObject(sales, SaleDTO.class);
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
            throw new DatabaseIntegrationException("This action is not possible because this sale is currently in use.");
        }
    }

    private void Hateoas(SaleDTO saleDTO) {
        saleDTO.add(linkTo(methodOn(SaleController.class).create(new ProductSaleCreateDTO()))
                .withRel("create").withType("POST"));
        saleDTO.add(linkTo(methodOn(SaleController.class).findById(saleDTO.getId()))
                .withSelfRel().withType(" GET"));
        saleDTO.add(linkTo(methodOn(SaleController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        saleDTO.add(linkTo(methodOn(SaleController.class).delete(saleDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
