package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.PurchaseController;
import com.projeto.sol_de_verao.dto.*;
import com.projeto.sol_de_verao.dto.createDTO.ProductPurchaseCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
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

        Purchase purchase = repositoryPurchase.save(new Purchase(
                customer,
                employee,
                productPurchaseCreateDTO.getPaymentMethod().getPaymentMethod(),
                productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments(),
                new Date()));

        BigDecimal sum = BigDecimal.ZERO;
        PaymentMethod paymentMethod = productPurchaseCreateDTO.getPaymentMethod().getPaymentMethod();

        for (ProductQuantityPurchaseDTO products : productPurchaseCreateDTO.getProductQuantity()) {
            Product product = repositoryProduct.findById(products.getProduct()).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found"));
            if (!product.getEnabled()) {
                throw new IllegalStateException("This Product is disabled.");
            }

            product.setQuantity(product.getQuantity() + products.getQuantity());
            repositoryProduct.save(product);

            BigDecimal totalValue = products.getCost().multiply(BigDecimal.valueOf(products.getQuantity()));
            sum = sum.add(totalValue);

            repositoryProductPurchase.save(new ProductPurchase(purchase,product,products.getQuantity(),totalValue));
        }

        if (paymentMethod.equals(PaymentMethod.CASH) ||
                paymentMethod.equals(PaymentMethod.PIX) ||
                paymentMethod.equals(PaymentMethod.DEBIT_CARD)) {

            if (productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments() != 1) {
                throw new InvalidPaymentMethodException("Cash, PIX and debit card payments cannot be divided into installments.");
            }

            repositoryAccountsPayable.save(new AccountsPayable(purchase,1,sum,new Date()));

        } else if (paymentMethod.equals(PaymentMethod.INSTALLMENTS) ||
                paymentMethod.equals(PaymentMethod.CREDIT_CARD)){

            BigDecimal installment = sum.divide(BigDecimal.valueOf(productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments()),2, RoundingMode.DOWN);
            for (int i = 1; i <= productPurchaseCreateDTO.getPaymentMethod().getNumberOfInstallments(); i++) {
                repositoryAccountsPayable.save(new AccountsPayable(purchase,i,installment,LocalDate.now().plusMonths(i),new Date()));
            }
        }

        return new ProductPurchaseDTO(purchase.getId(),
                ObjectMapper.parseObject(customer, CustomerDTORes.class),
                ObjectMapper.parseObject(employee, EmployeeDTORes.class),
                productPurchaseCreateDTO.getPaymentMethod(),
                productPurchaseCreateDTO.getProductQuantity(),
                sum,
                new Date());
    }

    public PagedModel<EntityModel<PurchaseDTO>> findAll(Pageable pageable) {

        Page<Purchase> purchase = repositoryPurchase.findAll(pageable);

        Page<PurchaseDTO> purchaseDTOS = purchase.map((x) -> {
            PurchaseDTO dto = ObjectMapper.parseObject(x, PurchaseDTO.class);
            Hateoas(dto);
            return dto;
        });

        return assembler.toModel(purchaseDTOS);
    }

    public PurchaseDTO findById(Long id) {
        logger.warn("Finding purchase");

        PurchaseDTO purchase = ObjectMapper.parseObject(repositoryPurchase.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found")), PurchaseDTO.class);

        Hateoas(purchase);

        return purchase;
    }

    public void delete(Long id) {
        logger.warn("Deleting purchase");

        Purchase purchase = repositoryPurchase.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field of purchase not found"));

        try {
            repositoryPurchase.delete(purchase);
        } catch (EntityNotFoundException e) {
            throw new DatabaseIntegrationException("This action is not possible because this purchase is currently in use.");
        }
    }

    private void Hateoas(PurchaseDTO purchaseDTO) {
        purchaseDTO.add(linkTo(methodOn(PurchaseController.class).create(new ProductPurchaseCreateDTO()))
                .withRel("create").withType("POST"));
        purchaseDTO.add(linkTo(methodOn(PurchaseController.class).findById(purchaseDTO.getId()))
                .withSelfRel().withType(" GET"));
        purchaseDTO.add(linkTo(methodOn(PurchaseController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        purchaseDTO.add(linkTo(methodOn(PurchaseController.class).delete(purchaseDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
