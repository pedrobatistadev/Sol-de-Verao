package com.projeto.sol_de_verao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.Objects;

public class ProductQuantitySaleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "The product ID cannot be empty.")
    @Positive(message = "The product ID cannot be 0 or negative.")
    private Long product;

    @NotNull(message = "The quantity cannot be empty.")
    @Positive(message = "The quantity cannot be 0 or negative.")
    private Integer quantity;

    public ProductQuantitySaleDTO() {
    }

    public ProductQuantitySaleDTO(Long product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductQuantitySaleDTO that)) return false;
        return Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
