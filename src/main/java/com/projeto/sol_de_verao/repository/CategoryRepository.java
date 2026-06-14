package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
