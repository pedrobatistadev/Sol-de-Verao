package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.dto.PaymentDTO;
import com.projeto.sol_de_verao.dto.ProductQuantityPurchaseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;

public class ProductPurchaseCreateDTO {

    @NotNull(message = "Customer ID cannot be empty.")
    @Positive(message = "Customer ID cannot be 0 or negative.")
    private Long customer;

    @NotNull(message = "Employee ID cannot be empty.")
    @Positive(message = "Employee ID cannot be 0 or negative.")
    private Long employee;

    @NotEmpty(message = "A purchase must include products.")
    @Valid
    private List<ProductQuantityPurchaseDTO> productQuantity;

    @NotNull(message = "The payment method cannot be empty.")
    @Valid
    private PaymentDTO paymentMethod;

    public ProductPurchaseCreateDTO() {
    }

    public ProductPurchaseCreateDTO(Long customer, Long employee, List<ProductQuantityPurchaseDTO> productQuantity, PaymentDTO paymentMethod) {
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

    public List<ProductQuantityPurchaseDTO> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<ProductQuantityPurchaseDTO> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public PaymentDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductPurchaseCreateDTO that)) return false;
        return Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(productQuantity, that.productQuantity) && Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, employee, productQuantity, paymentMethod);
    }
}
