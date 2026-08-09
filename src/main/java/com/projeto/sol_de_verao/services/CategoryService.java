package com.projeto.sol_de_verao.services;
import com.projeto.sol_de_verao.controllers.CategoryController;
import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.repository.CategoryRepository;
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
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO create(CategoryCreateDTO categoryCreateDTO) {

        logger.warn("Creating Category !");

        validation(categoryCreateDTO);

        Category category = ObjectMapper.parseObject(categoryCreateDTO, Category.class);

        var result = ObjectMapper.parseObject(repository.save(category), CategoryDTO.class);

        Hateoas(result);

        return result;

    }

    public CategoryDTO update(Long id, CategoryCreateDTO categoryCreateDTO) {

        logger.warn("Updating Category !");

        validation(categoryCreateDTO);

        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        category.setDescription(categoryCreateDTO.getDescription());

        var result = ObjectMapper.parseObject(repository.save(category), CategoryDTO.class);

        Hateoas(result);

        return result;
    }

    public CategoryDTO findById(Long id) {

        logger.warn("Finding Category !");

        var result = ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), CategoryDTO.class);

        Hateoas(result);

        return result;
    }

    public List<CategoryDTO> findAll() {

        logger.warn("Finding All Category");

        List<Category> categories = repository.findAll();


        var result = ObjectMapper.parseList(categories, CategoryDTO.class);

        result.forEach(this::Hateoas);

        return result;
    }

    public void delete(Long id) {

        logger.warn("Deleting Category");

        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(category);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this category is currently in use.");
        }
    }

    private void validation(CategoryCreateDTO categoryCreateDTO) {
        if (categoryCreateDTO.getDescription() == null || categoryCreateDTO.getDescription() == "") {
            throw new IllegalArgumentException("The description field cannot be empty.");
        }
    }

    private void Hateoas(CategoryDTO categoryDTO) {
        categoryDTO.add(linkTo(methodOn(CategoryController.class).create(ObjectMapper.parseObject(categoryDTO, CategoryCreateDTO.class)))
                .withRel("create").withType("POST"));
        categoryDTO.add(linkTo(methodOn(CategoryController.class).update(categoryDTO.getId(),ObjectMapper.parseObject(categoryDTO, CategoryCreateDTO.class)))
                .withRel("update").withType("PUT"));
        categoryDTO.add(linkTo(methodOn(CategoryController.class).findById(categoryDTO.getId()))
                .withSelfRel().withType(" GET"));
        categoryDTO.add(linkTo(methodOn(CategoryController.class).findAll())
                .withRel("findAll").withType("GET"));
        categoryDTO.add(linkTo(methodOn(CategoryController.class).delete(categoryDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
