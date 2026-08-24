package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.ProductSaleDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductSaleCreateDTO;
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

public interface SaleControllerDocs {

    @Operation(summary = "Create Sale", description = "Create Sale", tags = {"Sale"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductSaleDTO.class))))})
    ResponseEntity<ProductSaleDTO> create(@RequestBody ProductSaleCreateDTO sale);

    @Operation(summary = "Find Sale", description = "Find Sale", tags = {"Sale"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductSaleDTO.class))))})
    ResponseEntity<ProductSaleDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Sale", description = "Find All Sale", tags = {"Sale"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductSaleDTO.class))))})
    ResponseEntity<PagedModel<EntityModel<ProductSaleDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @Operation(summary = "Delete Sale", description = "Delete Sale", tags = {"Sale"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

}
