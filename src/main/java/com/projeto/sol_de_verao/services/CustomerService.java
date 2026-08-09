package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.CategoryController;
import com.projeto.sol_de_verao.controllers.CustomerController;
import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.model.Customers_Log;
import com.projeto.sol_de_verao.model.enums.Actions;
import com.projeto.sol_de_verao.repository.CustomerLogRepository;
import com.projeto.sol_de_verao.repository.CustomerRepository;
import com.projeto.sol_de_verao.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CustomerService {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");


    private Logger logger = LoggerFactory.getLogger(CustomerService.class.getName());

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerLogRepository repositoryLog;

    public CustomerDTO create(CustomerCreateDTO customerCreateDTO) {

        logger.warn("Creating Customer !");

        validation(customerCreateDTO);

        Customer customer = ObjectMapper.parseObject(customerCreateDTO, Customer.class);
        customer.setEnabled(true);
        customer.setCreationDate(new Date());

        Customer saved = repository.save(customer);

        repositoryLog.save(new Customers_Log(saved, Actions.CREATE,saved.getName() + " successfully created !",new Date()));

        var result = ObjectMapper.parseObject(saved, CustomerDTO.class);

        Hateoas(result);

        return result;
    }

    public CustomerDTO update(Long id, CustomerCreateDTO customerCreateDTO) {

        logger.warn("Updating Customer !");

        validation(customerCreateDTO);

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        Customer oldCustomer = new Customer(customer.getName(),customer.getCpf(), customer.getPhone(), customer.getCredit(), customer.getType(), customer.getDateBirth());

        customer.setName(customerCreateDTO.getName());
        customer.setCpf(customerCreateDTO.getCpf());
        customer.setPhone(customerCreateDTO.getPhone());
        customer.setCredit(customerCreateDTO.getCredit());
        customer.setType(customerCreateDTO.getType());
        customer.setDateBirth(customerCreateDTO.getDateBirth());

        Customer newCustomer = new Customer(customer.getName(),customer.getCpf(), customer.getPhone(), customer.getCredit(), customer.getType(), customer.getDateBirth());

        if (!oldCustomer.getName().equals(newCustomer.getName())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "Name changed from " + oldCustomer.getName() + " to " + newCustomer.getName(), new Date()));
        }

        if (!oldCustomer.getCpf().equals(newCustomer.getCpf())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "CPF changed from " + oldCustomer.getCpf() + " to " + newCustomer.getCpf(), new Date()));
        }

        if (!oldCustomer.getPhone().equals(newCustomer.getPhone())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "Phone changed from " + oldCustomer.getPhone() + " to " + newCustomer.getPhone(), new Date()));
        }

        if (!oldCustomer.getCredit().equals(newCustomer.getCredit())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "Credit changed from " + oldCustomer.getCredit() + " to " + newCustomer.getCredit(), new Date()));
        }

        if (!oldCustomer.getType().equals(newCustomer.getType())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "Type changed from " + oldCustomer.getType() + " to " + newCustomer.getType(), new Date()));
        }

        if (!oldCustomer.getDateBirth().equals(newCustomer.getDateBirth())) {
            repositoryLog.save(new Customers_Log(customer,Actions.UPDATE, "Date Birth changed from " + sdf1.format(oldCustomer.getDateBirth()) + " to " + sdf1.format(newCustomer.getDateBirth()), new Date()));
        }

        var result = ObjectMapper.parseObject(repository.save(customer), CustomerDTO.class);

        Hateoas(result);

        return result;
    }

    @Transactional
    public CustomerDTO disable(Long id) {

        logger.warn("Disabling Customer");

        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        repository.disable(id);

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        repositoryLog.save(new Customers_Log(customer,Actions.PATCH,"Disable customer " + customer.getName(), new Date()));
        var result = ObjectMapper.parseObject(customer, CustomerDTO.class);

        Hateoas(result);

        return result;
    }

    public CustomerDTO findById(Long id) {

        logger.warn("Finding Customer !");

        var result = ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), CustomerDTO.class);

        Hateoas(result);

        return result;
    }

    public Page<CustomerDTO> findAll(Pageable pageable) {

        logger.warn("Finding All Customers");

        Page<Customer> customers = repository.findAll(pageable);

        Page<CustomerDTO> result = customers.map((customer) -> {
            CustomerDTO dto = ObjectMapper.parseObject(customer, CustomerDTO.class);
            Hateoas(dto);
            return dto;
        });

        return result;
    }

    public void delete(Long id) {

        logger.warn("Deleting Customer");

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(customer);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this customer is currently in use.");
        }

        repositoryLog.save(new Customers_Log(customer,Actions.DELETE, "Customer " + customer.getName() + " delected successful", new Date()));
    }

    private void validation(CustomerCreateDTO customerCreateDTO) {
        if (customerCreateDTO.getName() == null || customerCreateDTO.getName() == "") {
            throw new IllegalArgumentException("The name field cannot be empty.");

        } else if (customerCreateDTO.getCpf() == null || customerCreateDTO.getCpf() == "") {
            throw new IllegalArgumentException("The cpf field cannot be empty.");

        } else if (customerCreateDTO.getPhone() == null || customerCreateDTO.getPhone() == "") {
            throw new IllegalArgumentException("The phone field cannot be empty.");

        } else if (customerCreateDTO.getCredit() == null) {
            throw new IllegalArgumentException("The credit field cannot be empty.");

        } else if (customerCreateDTO.getType() == null) {
            throw new IllegalArgumentException("The type field cannot be empty.");

        } else if (customerCreateDTO.getDateBirth() == null) {
            throw new IllegalArgumentException("The date birth field cannot be empty.");

        } else if (customerCreateDTO.getDateBirth().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The date of birth field cannot be later than the current date.");
        }
    }

    private void Hateoas(CustomerDTO customerDTO) {
        customerDTO.add(linkTo(methodOn(CustomerController.class).create(ObjectMapper.parseObject(customerDTO, CustomerCreateDTO.class)))
                .withRel("create").withType("POST"));
        customerDTO.add(linkTo(methodOn(CustomerController.class).update(customerDTO.getId(),ObjectMapper.parseObject(customerDTO, CustomerCreateDTO.class)))
                .withRel("update").withType("PUT"));
        customerDTO.add(linkTo(methodOn(CustomerController.class).findById(customerDTO.getId()))
                .withSelfRel().withType(" GET"));
        customerDTO.add(linkTo(methodOn(CustomerController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        customerDTO.add(linkTo(methodOn(CustomerController.class).disable(customerDTO.getId()))
                .withRel("disable").withType("PATCH"));
        customerDTO.add(linkTo(methodOn(CustomerController.class).delete(customerDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }

}
