package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.model.Category;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

public class ProductCreateDTO {

    private String name;

    private Long category;

    private Double unitPrice;

    private Integer quantity;

    private Integer inventory;

    private Boolean enabled;

    private Date creationDate;

    public ProductCreateDTO() {
        this.enabled =  true;
    }

    public ProductCreateDTO(String name, Long category, Double unitPrice, Integer quantity, Integer inventory, Boolean enabled, Date creationDate) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.inventory = inventory;
        this.enabled = true;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
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

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
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
        if (!(o instanceof ProductCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(quantity, that.quantity) && Objects.equals(inventory, that.inventory) && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, unitPrice, quantity, inventory, enabled, creationDate);
    }
}
