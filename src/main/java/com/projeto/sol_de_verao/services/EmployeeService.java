package com.projeto.sol_de_verao.services;
import com.projeto.sol_de_verao.controllers.EmployeeController;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.createDTO.EmployeeCreateDTO;
import com.projeto.sol_de_verao.exception.DatabaseIntegrationException;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Employee;
import com.projeto.sol_de_verao.repository.EmployeeRepository;
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
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class.getName());

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PagedResourcesAssembler assembler;

    public EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO) {

        logger.warn("Creating Employee !");

        Employee employee = ObjectMapper.parseObject(employeeCreateDTO, Employee.class);
        employee.setEnabled(true);
        employee.setCreationDate(new Date());

        var result = ObjectMapper.parseObject(repository.save(employee), EmployeeDTO.class);

        Hateoas(result);

        return result;

    }

    public EmployeeDTO update(Long id, EmployeeCreateDTO employeeCreateDTO) {

        logger.warn("Updating Employee !");

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

    public PagedModel<EntityModel<EmployeeDTO>> findAll(Pageable pageable) {

        logger.warn("Finding All Employees");

        Page<Employee> employee = repository.findAll(pageable);

        var result = employee.map((employe) -> {
            var dto = ObjectMapper.parseObject(employe, EmployeeDTO.class);

            Hateoas(dto);
            return dto;
        });

        return assembler.toModel(result);
    }

    public void delete(Long id) {

        logger.warn("Deleting Employee");

        Employee employee = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));

        try {
            repository.delete(employee);
        } catch(Exception e) {
            throw new DatabaseIntegrationException("This action is not possible because this employee is currently in use.");
        }
    }

    private void Hateoas(EmployeeDTO employeeDTO) {
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).create(ObjectMapper.parseObject(employeeDTO, EmployeeCreateDTO.class)))
                .withRel("create").withType("POST"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).update(employeeDTO.getId(),ObjectMapper.parseObject(employeeDTO, EmployeeCreateDTO.class)))
                .withRel("update").withType("PUT"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class))
                .withRel("disable").withType("PATCH"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).findById(employeeDTO.getId()))
                .withSelfRel().withType(" GET"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).findAll(0,12,"asc"))
                .withRel("findAll").withType("GET"));
        employeeDTO.add(linkTo(methodOn(EmployeeController.class).delete(employeeDTO.getId()))
                .withRel("delete").withType("DELETE"));
    }
}
