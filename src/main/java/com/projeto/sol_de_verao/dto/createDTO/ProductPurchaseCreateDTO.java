package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.dto.ProductQuantityDTO;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.model.Employee;

import java.util.List;
import java.util.Objects;

public class ProductPurchaseCreateDTO {

    private Customer customer;

    private Employee employee;

    private List<ProductQuantityDTO> productQuantity;

    public ProductPurchaseCreateDTO() {
    }

    public ProductPurchaseCreateDTO(Customer customer, Employee employee, List<ProductQuantityDTO> productQuantity) {
        this.customer = customer;
        this.employee = employee;
        this.productQuantity = productQuantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<?> getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductPurchaseCreateDTO that)) return false;
        return Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, employee, productQuantity);
    }
}
