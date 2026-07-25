package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.InventoryDTO;
import com.projeto.sol_de_verao.dto.InventoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.InventoryCreateDTO;
import com.projeto.sol_de_verao.dto.createDTO.InventoryCreateDTO;
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

public interface InventoryControllerDocs {

    @Operation(summary = "Create Inventory", description = "Create Inventory", tags = {"Inventory"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = InventoryDTO.class))))})
    ResponseEntity<InventoryDTO> create(@RequestBody InventoryCreateDTO Inventory);

    @Operation(summary = "Find Inventory", description = "Find Inventory", tags = {"Inventory"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = InventoryDTO.class))))})
    ResponseEntity<InventoryDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Inventory", description = "Find All Inventory", tags = {"Inventory"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = InventoryDTO.class))))})
    ResponseEntity<List<InventoryDTO>> findAll();

    @Operation(summary = "Delete Inventory", description = "Delete Inventory", tags = {"Inventory"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Inventory", description = "Updating Inventory", tags = {"Inventory"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<InventoryDTO> update(@PathVariable Long id, @RequestBody InventoryCreateDTO InventoryCreateDTO);
}
