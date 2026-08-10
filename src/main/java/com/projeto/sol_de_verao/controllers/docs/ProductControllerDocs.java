package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.CustomerDTO;
import com.projeto.sol_de_verao.dto.EmployeeDTO;
import com.projeto.sol_de_verao.dto.ProductDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductCreateDTO;
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

import java.util.List;

public interface ProductControllerDocs {

    @Operation(summary = "Create Product", description = "Create Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))})
    ResponseEntity<ProductDTO> create(@RequestBody ProductCreateDTO product);

    @Operation(summary = "Find Product", description = "Find Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))})
    ResponseEntity<ProductDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Product", description = "Find All Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))})
    ResponseEntity<PagedModel<EntityModel<ProductDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @Operation(summary = "Delete Product", description = "Delete Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Product", description = "Updating Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO);

    @Operation(summary = "Disabling Product", description = "Disabling Product", tags = {"Product"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<ProductDTO> disable(@PathVariable Long id);
}
