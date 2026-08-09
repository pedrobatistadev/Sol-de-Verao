package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Employee AS e SET e.enabled = false WHERE e.id = :id")
    void disable(@Param("id") Long id);
}
