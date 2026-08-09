package com.projeto.sol_de_verao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CustomerDTO extends RepresentationModel<CustomerDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Boolean credit;

    private TypeCustomer type;

    private Boolean enabled;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    public CustomerDTO() {
        this.enabled = true;
    }

    public CustomerDTO(String name, Boolean credit, TypeCustomer type, Boolean enabled, Date creationDate) {
        this.name = name;
        this.credit = credit;
        this.type = type;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomerDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(credit, that.credit) && type == that.type && Objects.equals(enabled, that.enabled) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credit, type, enabled, creationDate);
    }
}
