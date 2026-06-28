package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
