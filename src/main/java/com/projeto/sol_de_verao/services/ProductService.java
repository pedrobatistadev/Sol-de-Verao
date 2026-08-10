package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.controllers.InventoryController;
import com.projeto.sol_de_verao.controllers.ProductController;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.InventoryDTO;
import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.dto.createDTO.InventoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.*;
import com.projeto.sol_de_verao.model.enums.Actions;
import com.projeto.sol_de_verao.repository.CategoryRepository;
import com.projeto.sol_de_verao.repository.InventoryRepository;
import com.projeto.sol_de_verao.repository.ProductLogRepository;
import com.projeto.sol_de_verao.repository.ProductRepository;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository repositoryCategory;

    @Autowired
    private InventoryRepository repositoryInventory;

    @Autowired
    private ProductLogRepository repositoryLog;

    @Autowired
    public PagedResourcesAssembler assembler;


    public ProductDTO create(ProductCreateDTO productCreateDTO) {

        logger.warn("Creating Product !");

        validation(productCreateDTO);

        Category category = repositoryCategory.findByName(productCreateDTO.getCategory()).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        Inventory inventory = repositoryInventory.findByName(productCreateDTO.getInventory()).orElseThrow(() -> new EntityNotFoundException("Inventory Not Found"));

        Product product = ObjectMapper.parseObject(productCreateDTO, Product.class);
        product.setCategory(category);
        product.setInventory(inventory);
        product.setEnabled(true);
        product.setCreationDate(new Date());

        Product saved = repository.save(product);

        repositoryLog.save(new Product_Log(saved, Actions.CREATE, saved.getName() + " successfully created !", new Date()));

        var result = ObjectMapper.parseObject(saved, ProductDTO.class);

        Hateoas(result);

        return result;

    }

    public ProductDTO update(Long id, ProductCreateDTO productCreateDTO) {

        logger.warn("Updating Product !");

        validation(productCreateDTO);

        Product product = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        Product oldProduct = new Product(product.getName(), product.getCategory(), product.getUnitPrice(),product.getQuantity(), product.getInventory());

        product.setName(productCreateDTO.getName());

        Category category = repositoryCategory.findByName(productCreateDTO.getCategory()).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        product.setCategory(category);

        product.setUnitPrice(productCreateDTO.getUnitPrice());
        product.setQuantity(productCreateDTO.getQuantity());

        Inventory inventory = repositoryInventory.findByName(productCreateDTO.getInventory()).orElseThrow(() -> new EntityNotFoundException("Inventory Not Found"));
        product.setInventory(inventory);

        Product newProduct = new Product(product.getName(), product.getCategory(), product.getUnitPrice(),product.getQuantity(), product.getInventory());


        if (!oldProduct.getName().equals(newProduct.getName())) {
            repositoryLog.save(new Product_Log(product, Actions.UPDATE, "Name changed from " + oldProduct.getName() + " to " + newProduct.getName(), new Date()));
        }

        if (!oldProduct.getCategory().equals(newProduct.getCategory())) {
            repositoryLog.save(new Product_Log(product, Actions.UPDATE, "Category changed from " + oldProduct.getCategory().getDescription() + " to " + newProduct.getCategory().getDescription(), new Date()));
        }

        if (!oldProduct.getUnitPrice().equals(newProduct.getUnitPrice())) {
            repositoryLog.save(new Product_Log(product, Actions.UPDATE, "Unit Price changed from " + oldProduct.getUnitPrice() + " to " + newProduct.getUnitPrice(), new Date()));
        }

        if (!oldProduct.getQuantity().equals(newProduct.getQuantity())) {
            repositoryLog.save(new Product_Log(product, Actions.UPDATE, "Quantity changed from " + oldProduct.getQuantity() + " to " + newProduct.getQuantity(), new Date()));
        }

        if (!oldProduct.getInventory().equals(newProduct.getInventory())) {
            repositoryLog.save(new Product_Log(product, Actions.UPDATE, "Inventory changed from " + oldProduct.getInventory().getDescription() + " to " + newProduct.getInventory().getDescription(), new Date()));
        }

        var result = ObjectMapper.parseObject(repository.save(product), ProductDTO.class);

        Hateoas(result);

        return result;
    }

    @Transactional
    public ProductDTO disable(Long id) {

        logger.warn("Disabling Product !");

        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        repository.disable(id);

        Product product = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        repositoryLog.save(new Product_Log(product,Actions.PATCH, "Disable product " + product.getName(),new Date()));

        var result = ObjectMapper.parseObject(product, ProductDTO.class);

        Hateoas(result);

        return result;
    }

    public ProductDTO findById(Long id) {

        logger.warn("Finding Product !");

        var result = ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), ProductDTO.class);

        Hateoas(result);

        return result;
    }

    public PagedModel<EntityModel<ProductDTO>> findAll(Pageable pageable) {

        logger.warn("Finding All Products");

        Page<Product> products = repository.findAll(pageable);

        Page<ProductDTO> result = products.map((product) -> {
            var dto = ObjectMapper.parseObject(product, ProductDTO.class);
            Hateoas(dto);
            return dto;
        });

        return assembler.toModel(result);
    }

    public void delete(Long id) {

        logger.warn("Deleting Product");

        Product product = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(product);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this product is currently in use.");
        }

        repositoryLog.save(new Product_Log(product,Actions.DELETE, "Product " + product.getName() + " delected successful", new Date()));
    }

    private void validation(ProductCreateDTO productCreateDTO) {
        if (productCreateDTO.getName() == null || productCreateDTO.getName() == "") {
            throw new IllegalArgumentException("The name field cannot be empty.");

        } else if (productCreateDTO.getCategory() == null || productCreateDTO.getCategory() == "") {
            throw new IllegalArgumentException("The category field cannot be empty.");

        } else if (productCreateDTO.getUnitPrice() == null || productCreateDTO.getUnitPrice() == 0.00) {
            throw new IllegalArgumentException("The unit price field cannot be empty or 0.");

        } else if (productCreateDTO.getUnitPrice() <= 0.00) {
            throw new IllegalArgumentException("The unit price field cannot be less than 0.");

        } else if (productCreateDTO.getQuantity() == null || productCreateDTO.getQuantity() == 0) {
            throw new IllegalArgumentException("The quantity field cannot be empty or 0.");
        }
        else if (productCreateDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("The quantity field cannot be less than 0.");

        }else if (productCreateDTO.getInventory() == null || productCreateDTO.getInventory() == "") {
            throw new IllegalArgumentException("The inventory field cannot be empty.");

        }
    }

    private void Hateoas(ProductDTO productDTO) {
        productDTO.add(linkTo(methodOn(ProductController.class).create(ObjectMapper.parseObject(productDTO, ProductCreateDTO.class)))
                .withRel("create").withType("POST"));
        productDTO.add(linkTo(methodOn(ProductController.class).update(productDTO.getId(),ObjectMapper.parseObject(productDTO, ProductCreateDTO.class)))
                .withRel("update").withType("PUT"));
        productDTO.add(linkTo(methodOn(ProductController.class))
                .withRel("disable").withType("PATCH"));
        productDTO.add(linkTo(methodOn(ProductController.class).findById(productDTO.getId()))
                .withSelfRel().withType(" GET"));
        productDTO.add(linkTo(methodOn(ProductController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        productDTO.add(linkTo(methodOn(ProductController.class).delete(productDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
