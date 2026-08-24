package com.projeto.sol_de_verao.unitTests.Mock;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.model.enums.TypeCustomer;
import org.springframework.aop.support.Pointcuts;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockCustomer {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Customer mockEntity(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Pedro Lucas");
        customer.setCpf("68451303889");
        customer.setPhone("44999313367");
        customer.setCredit(true);
        customer.setType(TypeCustomer.CUSTOMER);
        customer.setEnabled(true);
        customer.setDateBirth(LocalDate.parse("24/04/2006", dtf));
        customer.setCreationDate(new Date());

        return customer;
    }

    public CustomerDTO mockEntityDto(Long id) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setName("Airton Batista");
        customerDTO.setCredit(true);
        customerDTO.setType(TypeCustomer.CUSTOMER);
        customerDTO.setEnabled(true);
        customerDTO.setCreationDate(new Date());

        return customerDTO;
    }

    public CustomerCreateDTO mockEntityCreateDto() {
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO();
        customerCreateDTO.setName("Isadora Alves");
        customerCreateDTO.setCpf("08781022808");
        customerCreateDTO.setPhone("44999313337");
        customerCreateDTO.setCredit(true);
        customerCreateDTO.setType(TypeCustomer.CUSTOMER);
        customerCreateDTO.setDateBirth(LocalDate.parse("02/10/2006", dtf));

        return customerCreateDTO;
    }

    public List<Customer> mockList() {
        List<Customer> customers = new ArrayList<>();
        for (var i = 1L; i <= 10L; i++) {
            Customer customer = mockEntity(i);
            customer.setName("Customer: " + i);
            customers.add(customer);
        }
        return customers;
    }
}
