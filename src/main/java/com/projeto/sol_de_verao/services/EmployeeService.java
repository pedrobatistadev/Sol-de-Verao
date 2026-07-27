package com.projeto.sol_de_verao.services;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.createDTO.EmployeeCreateDTO;
import com.projeto.sol_de_verao.mapper.ObjectMapper;
import com.projeto.sol_de_verao.model.Employee;
import com.projeto.sol_de_verao.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        return ObjectMapper.parseObject(repository.save(employee), EmployeeDTO.class);

    }

    public EmployeeDTO update(Long id, EmployeeCreateDTO employeeCreateDTO) {

        logger.warn("Updating Employee !");

        validation(employeeCreateDTO);

        Employee employee = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found"));
        employee.setName(employeeCreateDTO.getName());
        employee.setCpf(employeeCreateDTO.getCpf());
        employee.setPhone(employeeCreateDTO.getPhone());
        employee.setDateBirth(employeeCreateDTO.getDateBirth());

        return ObjectMapper.parseObject(repository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO findById(Long id) {

        logger.warn("Finding Employee !");

        return ObjectMapper.parseObject(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID field not found")), EmployeeDTO.class);
    }

    public List<EmployeeDTO> findAll() {

        logger.warn("Finding All Employees");

        List<Employee> categories = repository.findAll();

        return ObjectMapper.parseList(categories, EmployeeDTO.class);
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
}
