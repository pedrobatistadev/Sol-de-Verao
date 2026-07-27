package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT I FROM Inventory AS I WHERE I.description LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Inventory> findByName(@Param("name") String name);
}
