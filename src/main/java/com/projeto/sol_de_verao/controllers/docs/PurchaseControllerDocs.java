package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.ProductPurchaseDTO;
import com.projeto.sol_de_verao.dto.createDTO.ProductPurchaseCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface PurchaseControllerDocs {

    @Operation(summary = "Create Purchase", description = "Create Purchase", tags = {"Purchase"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductPurchaseDTO.class))))})
    ResponseEntity<ProductPurchaseDTO> create(@RequestBody ProductPurchaseCreateDTO purchase);

    @Operation(summary = "Find Purchase", description = "Find Purchase", tags = {"Purchase"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductPurchaseDTO.class))))})
    ResponseEntity<ProductPurchaseDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Purchase", description = "Find All Purchase", tags = {"Purchase"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductPurchaseDTO.class))))})
    ResponseEntity<PagedModel<EntityModel<ProductPurchaseDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                        @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @Operation(summary = "Delete Purchase", description = "Delete Purchase", tags = {"Purchase"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);
}
