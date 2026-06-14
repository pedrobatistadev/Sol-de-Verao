package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
