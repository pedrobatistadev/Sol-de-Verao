package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.PurchaseControllerDocs;
import com.projeto.sol_de_verao.dto.ProductPurchaseDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductPurchaseCreateDTO;
import com.projeto.sol_de_verao.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/purchase")
public class PurchaseController implements PurchaseControllerDocs {

    @Autowired
    private PurchaseService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductPurchaseDTO> create(@RequestBody ProductPurchaseCreateDTO productPurchaseCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(productPurchaseCreateDTO));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductPurchaseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<ProductPurchaseDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                               @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
