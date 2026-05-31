package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
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

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dateBirth", nullable = false)
    private Date dateBirth;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    public Customer() {
        this.credit = false;
        this.enabled = true;
    }

    public Customer(Long id, String name, String cpf, String phone, Date dateBirth, Date creationDate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.credit = false;
        this.enabled = true;
        this.dateBirth = dateBirth;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(cpf, customer.cpf) && Objects.equals(phone, customer.phone) && Objects.equals(credit, customer.credit) && Objects.equals(enabled, customer.enabled) && Objects.equals(dateBirth, customer.dateBirth) && Objects.equals(creationDate, customer.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, phone, credit, enabled, dateBirth, creationDate);
    }
}
