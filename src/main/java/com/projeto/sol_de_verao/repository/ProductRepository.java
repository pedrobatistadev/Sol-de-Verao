package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product AS p SET p.enabled = false WHERE p.id = :id")
    void disable(@Param("id") Long id);
}
