package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.dto.ProductQuantityDTO;
import com.projeto.sol_de_verao.model.enums.PaymentMethod;

import java.util.List;
import java.util.Objects;

public class ProductSaleCreateDTO {

    private Long customer;

    private Long employee;

    private List<ProductQuantityDTO> productQuantity;

    private String paymentMethod;

    public ProductSaleCreateDTO() {
    }

    public ProductSaleCreateDTO(Long customer, Long employee, List<ProductQuantityDTO> productQuantity, String paymentMethod) {
        this.customer = customer;
        this.employee = employee;
        this.productQuantity = productQuantity;
        this.paymentMethod = paymentMethod;
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

    public List<ProductQuantityDTO> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<ProductQuantityDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductSaleCreateDTO that)) return false;
        return Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity) && Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, employee, productQuantity, paymentMethod);
    }
}
