package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
