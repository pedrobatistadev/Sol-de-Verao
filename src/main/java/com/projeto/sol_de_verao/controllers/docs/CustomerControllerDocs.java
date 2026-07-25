package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerControllerDocs {
    
    @Operation(summary = "Create Customer", description = "Create Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> create(@RequestBody CustomerCreateDTO Customer);

    @Operation(summary = "Find Customer", description = "Find Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Customer", description = "Find All Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<List<CustomerDTO>> findAll();

    @Operation(summary = "Delete Customer", description = "Delete Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Customer", description = "Updating Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerCreateDTO CustomerCreateDTO);
    
}
