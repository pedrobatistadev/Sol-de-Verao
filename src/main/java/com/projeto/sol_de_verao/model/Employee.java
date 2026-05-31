package com.projeto.sol_de_verao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

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

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dateBirth", nullable = false)
    private Date dateBirth;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    public Employee() {
        this.enabled = true;
    }

    public Employee(Long id, String name, String cpf, String phone, Date dateBirth, Date creationDate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
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
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(cpf, employee.cpf) && Objects.equals(phone, employee.phone) && Objects.equals(enabled, employee.enabled) && Objects.equals(dateBirth, employee.dateBirth) && Objects.equals(creationDate, employee.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, phone, enabled, dateBirth, creationDate);
    }
}
