package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.createDTO.CustomerCreateDTO;
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

public interface CustomerControllerDocs {
    
    @Operation(summary = "Create Customer", description = "Create Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> create(@RequestBody CustomerCreateDTO customer);

    @Operation(summary = "Find Customer", description = "Find Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Customer", description = "Find All Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<PagedModel<EntityModel<CustomerDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                 @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @Operation(summary = "Delete Customer", description = "Delete Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Customer", description = "Updating Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerCreateDTO customerCreateDTO);

    @Operation(summary = "Disable Customer", description = "Disabling Customer", tags = {"Customer"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))})
    ResponseEntity<CustomerDTO> disable(@PathVariable Long id);
    
}
