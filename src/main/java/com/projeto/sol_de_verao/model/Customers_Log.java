package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.Actions;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "customers_log")
public class Customers_Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Actions action;

    @Column(name = "description")
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date")
    private Date creation_date;

    public Customers_Log() {
    }

    public Customers_Log(Customer customer, Actions action, String description, Date creation_date) {
        this.customer = customer;
        this.action = action;
        this.description = description;
        this.creation_date = creation_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customers_Log that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && action == that.action && Objects.equals(description, that.description) && Objects.equals(creation_date, that.creation_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, action, description, creation_date);
    }
}
