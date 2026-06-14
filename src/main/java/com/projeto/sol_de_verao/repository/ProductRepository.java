package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
