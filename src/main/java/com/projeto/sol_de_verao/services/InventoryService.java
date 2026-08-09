package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.CategoryController;
import com.projeto.sol_de_verao.controllers.InventoryController;
import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.InventoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.InventoryCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Inventory;
import com.projeto.sol_de_verao.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class InventoryService {

    private Logger logger = LoggerFactory.getLogger(InventoryService.class.getName());

    @Autowired
    private InventoryRepository repository;

    public InventoryDTO create(InventoryCreateDTO inventoryCreateDTO) {

        logger.warn("Creating Inventory");

        validation(inventoryCreateDTO);

        Inventory inventory = ObjectMapper.parseObject(inventoryCreateDTO, Inventory.class);

        var result = ObjectMapper.parseObject(repository.save(inventory), InventoryDTO.class);

        Hateoas(result);

        return result;

    }

    public InventoryDTO update(Long id, InventoryCreateDTO inventoryCreateDTO) {

        logger.warn("Updating Inventory !");

        validation(inventoryCreateDTO);

        Inventory inventory = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        inventory.setDescription(inventoryCreateDTO.getDescription());

        var result = ObjectMapper.parseObject(repository.save(inventory), InventoryDTO.class);

        Hateoas(result);

        return result;
    }

    public InventoryDTO findById(Long id) {

        logger.warn("Finding Inventory !");

        var result = ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), InventoryDTO.class);

        Hateoas(result);

        return result;
    }

    public List<InventoryDTO> findAll() {

        logger.warn("Finding All Inventory");

        List<Inventory> categories = repository.findAll();

        var result = ObjectMapper.parseList(categories, InventoryDTO.class);

        result.forEach(this::Hateoas);

        return result;

    }

    public void delete(Long id) {

        logger.warn("Deleting Inventory");

        Inventory inventory = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(inventory);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this inventory is currently in use.");
        }
    }
    
    private void validation(InventoryCreateDTO inventoryCreateDTO) {
        if (inventoryCreateDTO.getDescription() == null || inventoryCreateDTO.getDescription() == "") {
            throw new IllegalArgumentException("The description field cannot be empty.");
        }
    }

    private void Hateoas(InventoryDTO inventoryDTO) {
        inventoryDTO.add(linkTo(methodOn(InventoryController.class).create(ObjectMapper.parseObject(inventoryDTO, InventoryCreateDTO.class)))
                .withRel("create").withType("POST"));
        inventoryDTO.add(linkTo(methodOn(InventoryController.class).update(inventoryDTO.getId(),ObjectMapper.parseObject(inventoryDTO, InventoryCreateDTO.class)))
                .withRel("update").withType("PUT"));
        inventoryDTO.add(linkTo(methodOn(InventoryController.class).findById(inventoryDTO.getId()))
                .withSelfRel().withType(" GET"));
        inventoryDTO.add(linkTo(methodOn(InventoryController.class).findAll())
                .withRel("findAll").withType("GET"));
        inventoryDTO.add(linkTo(methodOn(InventoryController.class).delete(inventoryDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }

}
