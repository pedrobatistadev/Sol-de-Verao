package com.projeto.sol_de_verao.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EmployeeDTORes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    public EmployeeDTORes() {
        this.enabled = true;
    }

    public EmployeeDTORes(String name, Boolean enabled, Date creationDate) {
        this.name = name;
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
        if (!(o instanceof EmployeeDTORes that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, enabled, creationDate);
    }
}
