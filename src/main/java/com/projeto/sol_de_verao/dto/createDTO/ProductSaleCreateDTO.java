package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.dto.ProductQuantityDTO;

import java.util.List;
import java.util.Objects;

public class ProductSaleCreateDTO {

    private Long customer;

    private Long employee;

    private List<ProductQuantityDTO> productQuantity;


    public ProductSaleCreateDTO() {
    }

    public ProductSaleCreateDTO(Long customer, Long employee, List<ProductQuantityDTO> productQuantity) {
        this.customer = customer;
        this.employee = employee;
        this.productQuantity = productQuantity;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getEmployee() {
        return employee;
    }

    public void setEmployee(Long employee) {
        this.employee = employee;
    }

    public List<?> getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductSaleCreateDTO that)) return false;
        return Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, employee, productQuantity);
    }
}
