package com.projeto.sol_de_verao.services;
import com.projeto.sol_de_verao.controllers.CategoryController;
import com.projeto.sol_de_verao.controllers.EmployeeController;
import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.EmployeeCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Employee;
import com.projeto.sol_de_verao.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class.getName());

    @Autowired
    private EmployeeRepository repository;

    public EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO) {

        logger.warn("Creating Employee !");

        validation(employeeCreateDTO);

        Employee employee = ObjectMapper.parseObject(employeeCreateDTO, Employee.class);
        employee.setEnabled(true);
        employee.setCreationDate(new Date());

        var result = ObjectMapper.parseObject(repository.save(employee), EmployeeDTO.class);

        Hateoas(result);

        return result;

    }

    public EmployeeDTO update(Long id, EmployeeCreateDTO employeeCreateDTO) {

        logger.warn("Updating Employee !");

        validation(employeeCreateDTO);

        Employee employee = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        employee.setName(employeeCreateDTO.getName());
        employee.setCpf(employeeCreateDTO.getCpf());
        employee.setPhone(employeeCreateDTO.getPhone());
        employee.setDateBirth(employeeCreateDTO.getDateBirth());

        var result = ObjectMapper.parseObject(repository.save(employee), EmployeeDTO.class);

        Hateoas(result);

        return result;
    }

    @Transactional
    public EmployeeDTO disable(Long id) {

        logger.warn("Disabling Employee !");

        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        repository.disable(id);

        Employee employee = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        var result = ObjectMapper.parseObject(employee, EmployeeDTO.class);

        Hateoas(result);

        return result;
    }

    public EmployeeDTO findById(Long id) {

        logger.warn("Finding Employee !");

        var result = ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), EmployeeDTO.class);

        Hateoas(result);

        return result;
    }

    public List<EmployeeDTO> findAll() {

        logger.warn("Finding All Employees");

        List<Employee> categories = repository.findAll();

        var result = ObjectMapper.parseList(categories, EmployeeDTO.class);

        result.forEach(this::Hateoas);

        return result;
    }

    public void delete(Long id) {

        logger.warn("Deleting Employee");

        Employee employee = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(employee);
        } catch(Exception e) {
            throw new DataIntegrityViolationException("This action is not possible because this employee is currently in use.");
        }
    }

    private void validation(EmployeeCreateDTO employeeCreateDTO) {
        if (employeeCreateDTO.getName() == null || employeeCreateDTO.getName() == "") {
            throw new IllegalArgumentException("The name field cannot be empty.");

        } else if (employeeCreateDTO.getCpf() == null || employeeCreateDTO.getCpf() == "") {
            throw new IllegalArgumentException("The cpf field cannot be empty.");

        } else if (employeeCreateDTO.getPhone() == null || employeeCreateDTO.getPhone() == "") {
            throw new IllegalArgumentException("The phone field cannot be empty.");

        } else if (employeeCreateDTO.getDateBirth() == null) {
            throw new IllegalArgumentException("The date birth field cannot be empty.");

        } else if (employeeCreateDTO.getDateBirth().after(new Date())) {
            throw new IllegalArgumentException("The date of birth field cannot be later than the current date.");
        }
    }

    private void Hateoas(EmployeeDTO employeeDTO) {
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).create(ObjectMapper.parseObject(employeeDTO, EmployeeCreateDTO.class)))
                .withRel("create").withType("POST"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).update(employeeDTO.getId(),ObjectMapper.parseObject(employeeDTO, EmployeeCreateDTO.class)))
                .withRel("update").withType("PUT"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).findById(employeeDTO.getId()))
                .withSelfRel().withType(" GET"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).findAll())
                .withRel("findAll").withType("GET"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).delete(employeeDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
