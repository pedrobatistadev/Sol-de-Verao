package com.projeto.sol_de_verao.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_sale")
public class ProductSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "totalValue", nullable = false)
    private Double totalValue;

    public ProductSale() {
    }

    public ProductSale(Sale sale, Product product, Integer quantity, Double totalValue) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductSale that)) return false;
        return Objects.equals(sale, that.sale) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(totalValue, that.totalValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product, quantity, totalValue);
    }
}
