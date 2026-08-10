package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.ProductControllerDocs;
import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductCreateDTO;
import com.projeto.sol_de_verao.services.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/product")
public class ProductController implements ProductControllerDocs {
    
    @Autowired
    private ProductService service;
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(productCreateDTO));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, productCreateDTO));
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductDTO> disable(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.disable(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                       @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
