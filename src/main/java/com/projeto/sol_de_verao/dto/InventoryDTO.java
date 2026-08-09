package com.projeto.sol_de_verao.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class InventoryDTO extends RepresentationModel<InventoryDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;

    public InventoryDTO() {
    }

    public InventoryDTO(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InventoryDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
