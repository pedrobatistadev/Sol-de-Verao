package com.projeto.sol_de_verao.dto;

import com.projeto.sol_de_verao.model.enums.PaymentMethod;

import java.util.Objects;

public class PaymentDTO {

    private PaymentMethod paymentMethod;

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
