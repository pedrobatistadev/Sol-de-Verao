package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.EmployeeControllerDocs;
import com.projeto.sol_de_verao.controllers.docs.EmployeeControllerDocs;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.createDTO.EmployeeCreateDTO;
import com.projeto.sol_de_verao.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/v1/employee")
public class EmployeeController implements EmployeeControllerDocs {

    @Autowired
    private EmployeeService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeCreateDTO employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(employee));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, employeeCreateDTO));
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<EmployeeDTO> disable(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.disable(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete (@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
