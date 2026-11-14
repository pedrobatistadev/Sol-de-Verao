package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category AS c WHERE c.description LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Category> findByName(@Param("name") String name);
}
