package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.CustomerDTO;
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
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        return ObjectMapper.parseObject(saved, CustomerDTO.class);
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

        return ObjectMapper.parseObject(repository.save(customer), CustomerDTO.class);
    }

    public CustomerDTO findById(Long id) {

        logger.warn("Finding Customer !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), CustomerDTO.class);
    }

    public List<CustomerDTO> findAll() {

        logger.warn("Finding All Customers");

        List<Customer> customers = repository.findAll();

        return ObjectMapper.parseList(customers, CustomerDTO.class);
    }

    public void delete(Long id) {

        logger.warn("Deleting Customer");

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(customer);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this customer is currently in use.");
        }
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

        } else if (customerCreateDTO.getDateBirth().after(new Date())) {
            throw new IllegalArgumentException("The date of birth field cannot be later than the current date.");
        }
    }

}
