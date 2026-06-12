package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Purchase() {

    }

    public Purchase(Customer customer, Employee employee, Date creationDate) {
        this.customer = customer;
        this.employee = employee;
        this.creationDate = creationDate;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Purchase purchase)) return false;
        return Objects.equals(id, purchase.id) && Objects.equals(customer, purchase.customer) && Objects.equals(employee, purchase.employee) && Objects.equals(creationDate, purchase.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, employee, creationDate);
    }
}
