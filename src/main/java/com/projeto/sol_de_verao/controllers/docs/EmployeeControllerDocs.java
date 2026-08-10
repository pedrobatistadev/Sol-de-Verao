package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.createDTO.EmployeeCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmployeeControllerDocs {

    @Operation(summary = "Create Employee", description = "Create Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))})
    ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeCreateDTO Employee);

    @Operation(summary = "Find Employee", description = "Find Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))})
    ResponseEntity<EmployeeDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Employee", description = "Find All Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))})
    ResponseEntity<PagedModel<EntityModel<EmployeeDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                 @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @Operation(summary = "Delete Employee", description = "Delete Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Employee", description = "Updating Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeCreateDTO EmployeeCreateDTO);

    @Operation(summary = "Disabling Employee", description = "Disabling Employee", tags = {"Employee"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<EmployeeDTO> disable(@PathVariable Long id);
}
