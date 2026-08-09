package com.projeto.sol_de_verao.dto.createDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class EmployeeCreateDTO {

    private String name;

    private String cpf;

    private String phone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateBirth;

    public EmployeeCreateDTO() {
    }

    public EmployeeCreateDTO(String name, String cpf, String phone, Date dateBirth) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.dateBirth = dateBirth;
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

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeCreateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf) && Objects.equals(phone, that.phone) && Objects.equals(dateBirth, that.dateBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, phone, dateBirth);
    }
}
