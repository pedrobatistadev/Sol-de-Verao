package com.projeto.sol_de_verao.dto.createDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@JsonPropertyOrder({
        "name",
        "cpf",
        "phone",
        "dateBirth"
})
public class EmployeeCreateDTO {

    @NotBlank(message = "The name field cannot be empty.")
    private String name;

    @NotBlank(message = "The cpf field cannot be empty.")
    @Size(min = 11, max = 11, message = "CPF must contain 11 characters.")
    private String cpf;

    @NotBlank(message = "The phone filed cannot be empty")
    @Size(min = 10, max = 11, message = "The phone number must have 10 or 11 characters.")
    private String phone;

    @NotNull(message = "The date birth field cannot be empty.")
    @PastOrPresent(message = "The date of birth cannot be later than the current date.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateBirth;

    public EmployeeCreateDTO() {
    }

    public EmployeeCreateDTO(String name, String cpf, String phone, LocalDate dateBirth) {
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

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
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
