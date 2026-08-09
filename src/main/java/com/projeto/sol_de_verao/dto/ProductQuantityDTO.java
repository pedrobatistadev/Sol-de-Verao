package com.projeto.sol_de_verao.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class ProductQuantityDTO extends RepresentationModel<ProductQuantityDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long product;

    private Integer quantity;

    public ProductQuantityDTO() {
    }

    public ProductQuantityDTO(Long product, Integer quantity) {
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
        if (!(o instanceof ProductQuantityDTO that)) return false;
        return Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
