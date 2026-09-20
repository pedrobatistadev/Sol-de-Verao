package com.projeto.sol_de_verao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Relation(collectionRelation = "product")
public class ProductDTO extends RepresentationModel<ProductDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private CategoryDTO category;

    private BigDecimal unitPrice;

    private Integer quantity;

    private InventoryDTO inventory;

    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    public ProductDTO() {
        this.enabled = true;
    }

    public ProductDTO(String name, CategoryDTO category, BigDecimal unitPrice,Integer quantity, InventoryDTO inventory, Date creationDate) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
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

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(quantity, that.quantity) && Objects.equals(inventory, that.inventory) && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, unitPrice, quantity, inventory, enabled, creationDate);
    }
}
