package com.projeto.sol_de_verao.repository;

import com.projeto.sol_de_verao.model.Customers_Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLogRepository extends JpaRepository<Customers_Log, Long> {
}
