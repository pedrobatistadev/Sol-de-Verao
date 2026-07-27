package com.projeto.sol_de_verao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.PaymentMethod;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProductSaleDTO {

    private Long id;

    private CustomerDTO customer;

    private EmployeeDTO employee;

    private List<ProductQuantityDTO> productQuantity;

    private PaymentMethod paymentMethod;

    private Double totalValue;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;

    public ProductSaleDTO() {
    }

    public ProductSaleDTO(CustomerDTO customer, EmployeeDTO employee, List<ProductQuantityDTO> productQuantity,PaymentMethod paymentMethod, Double totalValue, Date creationDate) {
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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public List<ProductQuantityDTO> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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
