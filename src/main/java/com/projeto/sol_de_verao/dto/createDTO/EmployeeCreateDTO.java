package com.projeto.sol_de_verao.dto.createDTO;

import java.util.Date;
import java.util.Objects;

public class EmployeeCreateDTO {

    private String name;

    private String cpf;

    private String phone;

    private Boolean enabled;

    private Date dateBirth;

    private Date creationDate;

    public EmployeeCreateDTO() {
        this.enabled = true;
    }

    public EmployeeCreateDTO(String name, String cpf, String phone, Boolean enabled, Date dateBirth, Date creationDate) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.enabled = enabled;
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
        if (!(o instanceof EmployeeCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(phone, that.phone) && Objects.equals(enabled, that.enabled) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, phone, enabled, dateBirth, creationDate);
    }
}
