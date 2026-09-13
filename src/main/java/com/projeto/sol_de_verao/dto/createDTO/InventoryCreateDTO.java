package com.projeto.sol_de_verao.dto.createDTO;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class InventoryCreateDTO {

    @NotBlank(message = "The description field cannot be empty.")
    private String description;

    public InventoryCreateDTO() {

    }

    public InventoryCreateDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InventoryCreateDTO that)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }
}
