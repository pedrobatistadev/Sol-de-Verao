package com.projeto.sol_de_verao.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products_purchases", uniqueConstraints = {@UniqueConstraint(columnNames = {"purchase_id", "product_id"})})
public class ProductPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    public ProductPurchase() {
    }

    public ProductPurchase(Purchase purchase, Product product, Integer quantity, BigDecimal totalValue) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductPurchase that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(purchase, that.purchase) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(totalValue, that.totalValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchase, product, quantity, totalValue);
    }
}
