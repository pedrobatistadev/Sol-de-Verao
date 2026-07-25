package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.InventoryControllerDocs;
import com.projeto.sol_de_verao.dto.InventoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.InventoryCreateDTO;
import com.projeto.sol_de_verao.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/inventory")
public class InventoryController implements InventoryControllerDocs {

    @Autowired
    private InventoryService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryCreateDTO category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(category));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<InventoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<InventoryDTO> update(@PathVariable Long id, @RequestBody InventoryCreateDTO InventoryCreateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, InventoryCreateDTO));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<InventoryDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
