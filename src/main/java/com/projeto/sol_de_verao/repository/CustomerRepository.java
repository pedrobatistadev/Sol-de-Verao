package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer as c SET c.enabled = false WHERE c.id = :id")
    void disable(@Param("id") Long id);
}
