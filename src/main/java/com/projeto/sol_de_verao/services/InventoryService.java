package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.dto.InventoryDTO;
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

@Service
public class InventoryService {

    private Logger logger = LoggerFactory.getLogger(InventoryService.class.getName());

    @Autowired
    private InventoryRepository repository;

    public InventoryDTO create(InventoryCreateDTO inventoryCreateDTO) {

        logger.warn("Creating Inventory");

        validation(inventoryCreateDTO);

        Inventory inventory = ObjectMapper.parseObject(inventoryCreateDTO, Inventory.class);

        return ObjectMapper.parseObject(repository.save(inventory), InventoryDTO.class);

    }

    public InventoryDTO update(Long id, InventoryCreateDTO inventoryCreateDTO) {

        logger.warn("Updating Inventory !");

        validation(inventoryCreateDTO);

        Inventory inventory = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        inventory.setDescription(inventoryCreateDTO.getDescription());

        return ObjectMapper.parseObject(repository.save(inventory), InventoryDTO.class);
    }

    public InventoryDTO findById(Long id) {

        logger.warn("Finding Inventory !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), InventoryDTO.class);
    }

    public List<InventoryDTO> findAll() {

        logger.warn("Finding All Inventory");

        List<Inventory> categories = repository.findAll();

        return ObjectMapper.parseList(categories, InventoryDTO.class);
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

}
