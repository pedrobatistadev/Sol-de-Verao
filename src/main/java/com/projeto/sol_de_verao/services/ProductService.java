package com.projeto.sol_de_verao.services;

import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.model.Inventory;
import com.projeto.sol_de_verao.model.Product;
import com.projeto.sol_de_verao.model.Product_Log;
import com.projeto.sol_de_verao.model.enums.Actions;
import com.projeto.sol_de_verao.repository.CategoryRepository;
import com.projeto.sol_de_verao.repository.InventoryRepository;
import com.projeto.sol_de_verao.repository.ProductLogRepository;
import com.projeto.sol_de_verao.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

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

        return ObjectMapper.parseObject(saved, ProductDTO.class);

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

        return ObjectMapper.parseObject(repository.save(product), ProductDTO.class);
    }

    public ProductDTO findById(Long id) {

        logger.warn("Finding Product !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), ProductDTO.class);
    }

    public List<ProductDTO> findAll() {

        logger.warn("Finding All Products");

        List<Product> categories = repository.findAll();

        return ObjectMapper.parseList(categories, ProductDTO.class);
    }

    public void delete(Long id) {

        logger.warn("Deleting Product");

        Product product = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(product);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this product is currently in use.");
        }
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
}
