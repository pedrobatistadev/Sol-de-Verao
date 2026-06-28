package com.projeto.sol_de_verao.dto.createDTO;

import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import java.util.Date;
import java.util.Objects;

public class CustomerCreateDTO {

    private String name;

    private String cpf;

    private String phone;

    private Boolean credit;

    private TypeCustomer type;

    private Boolean enabled;

    private Date dateBirth;

    private Date creationDate;

    public CustomerCreateDTO() {
        this.enabled = true;
    }

    public CustomerCreateDTO(String name, String cpf, String phone, Boolean credit, TypeCustomer type, Date dateBirth, Date creationDate) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.credit = credit;
        this.type = type;
        this.enabled = true;
        this.dateBirth = dateBirth;
        this.creationDate = creationDate;
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
        if (!(o instanceof CustomerCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(phone, that.phone) && Objects.equals(credit, that.credit) && type == that.type && Objects.equals(enabled, that.enabled) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, phone, credit, type, enabled, dateBirth, creationDate);
    }
}
