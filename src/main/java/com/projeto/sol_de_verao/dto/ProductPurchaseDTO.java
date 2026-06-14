package com.projeto.sol_de_verao.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProductPurchaseDTO {

    private Long id;

    private CustomerDTO customer;

    private EmployeeDTO employee;

    private List<ProductQuantityDTO> productQuantity;

    private Double totalValue;

    private Date creationDate;

    public ProductPurchaseDTO() {
    }

    public ProductPurchaseDTO(Long id, CustomerDTO customer, EmployeeDTO employee, List<ProductQuantityDTO> productQuantity, Double totalValue, Date creationDate) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.productQuantity = productQuantity;
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

    public List<?> getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductPurchaseDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity) && Objects.equals(totalValue, that.totalValue) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, employee, productQuantity, totalValue, creationDate);
    }
}
