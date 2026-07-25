package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.repository.CategoryRepository;
import com.projeto.sol_de_verao.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CustomerService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class.getName());

    @Autowired
    private CustomerRepository repository;

    public CustomerDTO create(CustomerCreateDTO customerCreateDTO) {

        logger.warn("Creating Customer !");

        validation(customerCreateDTO);

        Customer customer = ObjectMapper.parseObject(customerCreateDTO, Customer.class);

        return ObjectMapper.parseObject(repository.save(customer), CustomerDTO.class);
    }

    public CustomerDTO update(Long id, CustomerCreateDTO customerCreateDTO) {

        logger.warn("Updating Category !");

        validation(customerCreateDTO);

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        customer.setName(customerCreateDTO.getName());
        customer.setCpf(customerCreateDTO.getCpf());
        customer.setPhone(customerCreateDTO.getPhone());
        customer.setCredit(customerCreateDTO.getCredit());
        customer.setType(customerCreateDTO.getType());
        customer.setEnabled(customerCreateDTO.getEnabled());
        customer.setDateBirth(customerCreateDTO.getDateBirth());
        customer.setCreationDate(customerCreateDTO.getCreationDate());

        return ObjectMapper.parseObject(repository.save(customer), CustomerDTO.class);
    }

    public CustomerDTO findById(Long id) {

        logger.warn("Finding Category !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), CustomerDTO.class);
    }

    public List<CustomerDTO> findAll() {

        logger.warn("Finding All Category");

        List<Customer> customers = repository.findAll();

        return ObjectMapper.parseList(customers, CustomerDTO.class);
    }

    public void delete(Long id) {

        logger.warn("Deleting Category");

        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        repository.delete(customer);
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

        } else if (customerCreateDTO.getEnabled() == null) {
            throw new IllegalArgumentException("The enabled field cannot be empty.");

        } else if (customerCreateDTO.getDateBirth() == null) {
            throw new IllegalArgumentException("The date birth field cannot be empty.");

        } else if (customerCreateDTO.getDateBirth().after(new Date())) {
            throw new IllegalArgumentException("The date of birth field cannot be later than the current date.");

        } else if (customerCreateDTO.getCreationDate() == null) {
            throw new IllegalArgumentException("The creation date field cannot be empty.");
        }
    }

}
