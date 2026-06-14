package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.model.Category;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

public class ProductCreateDTO {

    private String name;

    private Category category;

    private Double unitPrice;

    private Boolean enabled;

    private Integer inventory;

    private Date creationDate;

    public ProductCreateDTO() {
        this.enabled =  true;
    }

    public ProductCreateDTO(String name, Category category, Double unitPrice, Boolean enabled, Integer inventory, Date creationDate) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.enabled = enabled;
        this.inventory = inventory;
        this.creationDate = creationDate;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(enabled, that.enabled) && Objects.equals(inventory, that.inventory) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, unitPrice, enabled, inventory, creationDate);
    }
}
