package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.CustomerControllerDocs;
import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import com.projeto.sol_de_verao.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/customer")
public class CustomerController implements CustomerControllerDocs {
    
    @Autowired
    private CustomerService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerCreateDTO customerCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(customerCreateDTO));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerCreateDTO customerCreateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, customerCreateDTO));
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<CustomerDTO> disable(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.disable(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
