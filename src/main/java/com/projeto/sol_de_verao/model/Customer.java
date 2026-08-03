package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "phone",nullable = false, length = 11)
    private String phone;

    @Column(name = "credit", nullable = false)
    private Boolean credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeCustomer type;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_birth", nullable = false)
    private LocalDate dateBirth;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Customer() {
        this.credit = false;
        this.enabled = true;
    }

    public Customer(String name, String cpf, String phone, TypeCustomer type, LocalDate dateBirth, Date creationDate) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.credit = false;
        this.type = type;
        this.enabled = true;
        this.dateBirth = dateBirth;
        this.creationDate = creationDate;
    }

    public Customer(String name, String cpf, String phone, Boolean credit, TypeCustomer type, LocalDate dateBirth) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.credit = credit;
        this.type = type;
        this.dateBirth = dateBirth;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    public TypeCustomer getType() {
        return type;
    }

    public void setType(TypeCustomer type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(cpf, customer.cpf) && Objects.equals(phone, customer.phone) && Objects.equals(credit, customer.credit) && type == customer.type && Objects.equals(enabled, customer.enabled) && Objects.equals(dateBirth, customer.dateBirth) && Objects.equals(creationDate, customer.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, phone, credit, type, enabled, dateBirth, creationDate);
    }
}
