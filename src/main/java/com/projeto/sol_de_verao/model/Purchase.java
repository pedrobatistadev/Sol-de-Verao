package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.PaymentMethod;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "number_of_installment")
    private Integer numberOfInstallment;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Purchase() {
    }

    public Purchase(Customer customer, Employee employee, PaymentMethod paymentMethod, Integer numberOfInstallment, Date creationDate) {
        this.customer = customer;
        this.employee = employee;
        this.paymentMethod = paymentMethod;
        this.numberOfInstallment = numberOfInstallment;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
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
        return Objects.equals(id, purchase.id) && Objects.equals(customer, purchase.customer) && Objects.equals(employee, purchase.employee) && paymentMethod == purchase.paymentMethod && Objects.equals(numberOfInstallment, purchase.numberOfInstallment) && Objects.equals(creationDate, purchase.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, employee, paymentMethod, numberOfInstallment, creationDate);
    }
}
