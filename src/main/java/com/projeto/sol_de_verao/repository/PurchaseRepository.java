package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
