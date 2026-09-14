package com.projeto.sol_de_verao.dto;

import com.projeto.sol_de_verao.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class PaymentDTO {

    @NotNull(message = "The payment method cannot be empty.")
    private PaymentMethod paymentMethod;

    @NotNull(message = "The number of installments cannot be empty.")
    @Positive(message = "The number of installments cannot be 0 or negative.")
    private Integer numberOfInstallments;

    public PaymentDTO() {
    }

    public PaymentDTO(PaymentMethod paymentMethod, Integer numberOfInstallments) {
        this.paymentMethod = paymentMethod;
        this.numberOfInstallments = numberOfInstallments;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PaymentDTO that)) return false;
        return paymentMethod == that.paymentMethod && Objects.equals(numberOfInstallments, that.numberOfInstallments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethod, numberOfInstallments);
    }
}
