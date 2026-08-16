package com.projeto.sol_de_verao.controllers;

import com.projeto.sol_de_verao.controllers.docs.SaleControllerDocs;
import com.projeto.sol_de_verao.dto.ProductSaleDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
import com.projeto.sol_de_verao.services.SaleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/sale")
public class SaleController implements SaleControllerDocs {

    @Autowired
    private SaleService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<ProductSaleDTO> create(@RequestBody ProductSaleCreateDTO sale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(sale));
    }

    @Override
    public ResponseEntity<ProductSaleDTO> update(Long id, ProductSaleCreateDTO sale) {
        return null;
    }

    @Override
    public ResponseEntity<ProductSaleDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<PagedModel<EntityModel<ProductSaleDTO>>> findAll(Integer page, Integer size, String direction) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }


}
