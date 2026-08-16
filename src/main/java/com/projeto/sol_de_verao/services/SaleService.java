package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.dto.ProductQuantityDTO;
import com.projeto.sol_de_verao.dto.ProductSaleDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.*;
import com.projeto.sol_de_verao.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


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

    public ProductSaleDTO create(ProductSaleCreateDTO productSaleCreateDTO) {
        logger.warn("Creating Sale");

        Customer customer = repositoryCustomer.findById(productSaleCreateDTO.getCustomer()).orElseThrow(() -> new EntityNotFoundException("ID field of Customer not found"));
        Employee employee = repositoryEmployee.findById(productSaleCreateDTO.getEmployee()).orElseThrow(() -> new EntityNotFoundException("ID field of Employee not found"));

        Sale sale = repositorySale.save(new Sale(customer,employee,productSaleCreateDTO.getPaymentMethod().getPaymentMethod(),productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(),new Date()));

        Double sum = 0.0;
        Double totalValue = 0.0;

        for (ProductQuantityDTO products : productSaleCreateDTO.getProductQuantity()) {
            Product product = repositoryProduct.findById(products.getProduct()).orElseThrow(() -> new EntityNotFoundException("ID field of Product not found"));
            Double unitPrice = product.getUnitPrice();
            totalValue = products.getQuantity() * unitPrice;
            sum += totalValue;

            repositoryProductSale.save(new ProductSale(sale,product,products.getQuantity(),totalValue));
        }

        if (productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments() == 1) {
            repositoryAccountsReceivable.save(new AccountsReceivable(sale,1,sum, LocalDate.now().plusMonths(1), new Date()));
        } else {
            Double installment = sum / productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments();
            for (int i = 1; i <= productSaleCreateDTO.getPaymentMethod().getNumberOfInstallments(); i++) {
                repositoryAccountsReceivable.save(new AccountsReceivable(sale, i, installment, LocalDate.now().plusMonths(i), new Date()));
            }
        }

        return new ProductSaleDTO(sale.getId(), ObjectMapper.parseObject(customer, CustomerDTORes.class),
                ObjectMapper.parseObject(employee, EmployeeDTORes.class),productSaleCreateDTO.getPaymentMethod(),
                productSaleCreateDTO.getProductQuantity(),sum, new Date());
    }
}
