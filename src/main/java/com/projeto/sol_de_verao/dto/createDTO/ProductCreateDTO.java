package com.projeto.sol_de_verao.dto.createDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductCreateDTO {

    @NotBlank(message = "The name field cannot be empty.")
    private String name;

    @NotBlank(message = "The category field cannot be empty.")
    private String category;

    @NotNull(message = "The unit price field cannot be empty")
    @Positive(message = "The value of unitPrice must be greater than zero.")
    private BigDecimal unitPrice;

    @NotNull(message = "The quantity field cannot be empty or 0.")
    @Positive(message = "The value of quantity must be greater than zero.")
    private Integer quantity;

    @NotBlank(message = "The inventory field cannot be empty.")
    private String inventory;

    public ProductCreateDTO () {
    }

    public ProductCreateDTO(String name, String category, BigDecimal unitPrice, Integer quantity, String inventory) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(quantity, that.quantity) && Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, unitPrice, quantity, inventory);
    }
}
