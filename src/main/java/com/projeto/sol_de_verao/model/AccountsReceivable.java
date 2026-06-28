package com.projeto.sol_de_verao.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "accounts_receivable")
public class AccountsReceivable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(name = "installment_number")
    private Integer installmentNumber;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "creation_date")
    private Date creationDate;

    public AccountsReceivable() {
    }

    public AccountsReceivable(Sale sale, Integer installmentNumber, Double totalValue, Date dueDate, Date creationDate) {
        this.sale = sale;
        this.installmentNumber = installmentNumber;
        this.totalValue = totalValue;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccountsReceivable that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sale, that.sale) && Objects.equals(installmentNumber, that.installmentNumber) && Objects.equals(totalValue, that.totalValue) && Objects.equals(dueDate, that.dueDate) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sale, installmentNumber, totalValue, dueDate, creationDate);
    }
}