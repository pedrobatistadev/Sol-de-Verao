package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.Actions;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "products_log")
public class Product_Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private Actions action;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Product_Log() {
    }

    public Product_Log(Product product, Actions action, String description, Date creationDate) {
        this.product = product;
        this.action = action;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product_Log that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product) && action == that.action && Objects.equals(description, that.description) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, action, description, creationDate);
    }
}
