package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.ProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, Long> {
}
