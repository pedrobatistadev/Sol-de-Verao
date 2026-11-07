package com.projeto.sol_de_verao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.sol_de_verao.dto.internal.CustomerDTORes;
import com.projeto.sol_de_verao.dto.internal.EmployeeDTORes;
import com.projeto.sol_de_verao.model.Customer;
import com.projeto.sol_de_verao.model.Employee;
import com.projeto.sol_de_verao.model.enums.PaymentMethod;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.Objects;

@Relation(collectionRelation = "sales")
public class SaleDTO extends RepresentationModel<SaleDTO> {

    private Long id;

    private CustomerDTORes customer;

    private EmployeeDTORes employee;

    private PaymentMethod paymentMethod;

    private Integer numberOfInstallment;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creationDate;

    public SaleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTORes getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTORes customer) {
        this.customer = customer;
    }

    public EmployeeDTORes getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTORes employee) {
        this.employee = employee;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleDTO saleDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, saleDTO.id) && Objects.equals(customer, saleDTO.customer) && Objects.equals(employee, saleDTO.employee) && paymentMethod == saleDTO.paymentMethod && Objects.equals(numberOfInstallment, saleDTO.numberOfInstallment) && Objects.equals(creationDate, saleDTO.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, customer, employee, paymentMethod, numberOfInstallment, creationDate);
    }
}
