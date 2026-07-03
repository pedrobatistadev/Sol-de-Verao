package com.projeto.sol_de_verao.services;
import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Category;
import com.projeto.sol_de_verao.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO create(CategoryCreateDTO categoryCreateDTO) {

        logger.warn("Creating Category !");

        validation(categoryCreateDTO);

        Category category = ObjectMapper.parseObject(categoryCreateDTO, Category.class);

        return ObjectMapper.parseObject(repository.save(category), CategoryDTO.class);

    }

    public CategoryDTO update(Long id, CategoryCreateDTO categoryCreateDTO) {

        logger.warn("Updating Category !");

        validation(categoryCreateDTO);


        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        category.setDescription(categoryCreateDTO.getDescription());

        return ObjectMapper.parseObject(repository.save(category), CategoryDTO.class);

    }

    public CategoryDTO findById(Long id) {

        logger.warn("Finding Category !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), CategoryDTO.class);
    }

    public List<CategoryDTO> findAll() {

        logger.warn("Finding All Category");

        List<Category> categories = repository.findAll();

        return ObjectMapper.parseList(categories, CategoryDTO.class);
    }

    public void delete(Long id) {

        logger.warn("Deleting Category");

        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        repository.delete(category);
    }

    private void validation(CategoryCreateDTO categoryCreateDTO) {
        if (categoryCreateDTO.getDescription() == null || categoryCreateDTO.getDescription() == "") {
            throw new IllegalArgumentException("The description field cannot be empty.");
        }
    }
}
