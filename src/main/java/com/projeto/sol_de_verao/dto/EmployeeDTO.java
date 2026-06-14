package com.projeto.sol_de_verao.dto;

import java.util.Date;
import java.util.Objects;

public class EmployeeDTO {

    private Long id;

    private String name;

    private String phone;

    private Boolean enabled;

    private Date creationDate;

    public EmployeeDTO() {
        this.enabled = true;
    }

    public EmployeeDTO(String name, String phone, Boolean enabled, Date creationDate) {
        this.name = name;
        this.phone = phone;
        this.enabled = enabled;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, enabled, creationDate);
    }
}
