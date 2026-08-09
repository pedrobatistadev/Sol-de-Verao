package com.projeto.sol_de_verao.dto.createDTO;

import jakarta.persistence.*;

import java.util.Objects;

public class ProductCreateDTO {

    private String name;

    private String category;

    private Double unitPrice;

    private Integer quantity;

    private String inventory;

    public ProductCreateDTO () {
    }

    public ProductCreateDTO(String name, String category, Double unitPrice, Integer quantity, String inventory) {
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
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
