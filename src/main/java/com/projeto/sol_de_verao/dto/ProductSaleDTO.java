package com.projeto.sol_de_verao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProductSaleDTO extends RepresentationModel<ProductSaleDTO> implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private CustomerDTORes customer;

    private EmployeeDTORes employee;

    private PaymentDTO paymentMethod;

    private List<ProductQuantityDTO> productQuantity;

    private Double totalValue;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;

    public ProductSaleDTO() {
    }

    public ProductSaleDTO(Long id, CustomerDTORes customer, EmployeeDTORes employee, PaymentDTO paymentMethod, List<ProductQuantityDTO> productQuantity, Double totalValue, Date creationDate) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.productQuantity = productQuantity;
        this.paymentMethod = paymentMethod;
        this.totalValue = totalValue;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTORes getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTORes customer) {
        this.customer = customer;
    }

    public EmployeeDTORes getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTORes employee) {
        this.employee = employee;
    }

    public List<ProductQuantityDTO> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public PaymentDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductSaleDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity) && paymentMethod == that.paymentMethod && Objects.equals(totalValue, that.totalValue) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, employee, productQuantity, paymentMethod, totalValue, creationDate);
    }
}
