package com.projeto.sol_de_verao.dto;

import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.model.Inventory;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

public class ProductDTO {

    private Long id;

    private String name;

    private Category category;

    private Double unitPrice;

    private Inventory inventory;

    private Boolean enabled;

    private Date creationDate;

    public ProductDTO() {
        this.enabled = true;
    }

    public ProductDTO(String name, Category category, Double unitPrice, Inventory inventory, Date creationDate) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.inventory = inventory;
        this.enabled = true;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(inventory, that.inventory) && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, unitPrice, inventory, enabled, creationDate);
    }
}
