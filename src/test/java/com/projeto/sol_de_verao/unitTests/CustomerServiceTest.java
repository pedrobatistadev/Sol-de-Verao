package com.projeto.sol_de_verao.unitTests;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.repository.CustomerLogRepository;
import com.projeto.sol_de_verao.repository.CustomerRepository;
import com.projeto.sol_de_verao.services.CustomerService;
import com.projeto.sol_de_verao.unitTests.Mock.MockCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private MockCustomer input;

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerLogRepository repositoryLog;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    void setUp() {
        input = new MockCustomer();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        CustomerCreateDTO customerCreateDTO = input.mockEntityCreateDto();
        Customer customer = input.mockEntity(1L);
        CustomerDTO customerDTO = input.mockEntityDto(1L);
        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = service.create(customerCreateDTO);

        assertNotNull(result);
        assertEquals("Pedro Lucas", result.getName());
    }

    @Test
    void update() {
    }

    @Test
    void disable() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }
}