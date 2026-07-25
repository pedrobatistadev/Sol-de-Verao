package com.projeto.sol_de_verao.controllers.docs;

import com.projeto.sol_de_verao.dto.CategoryDTO;
import com.projeto.sol_de_verao.dto.createDTO.CategoryCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface  CategoryControllerDocs {

    @Operation(summary = "Create Category", description = "Create Category", tags = {"Category"}, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))})
    ResponseEntity<CategoryDTO> create(@RequestBody CategoryCreateDTO category);

    @Operation(summary = "Find Category", description = "Find Category", tags = {"Category"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))})
    ResponseEntity<CategoryDTO> findById(@PathVariable Long id);

    @Operation(summary = "Find All Category", description = "Find All Category", tags = {"Category"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))})
    ResponseEntity<List<CategoryDTO>> findAll();

    @Operation(summary = "Delete Category", description = "Delete Category", tags = {"Category"}, responses = {
            @ApiResponse(description = "Success", responseCode = "204")})
    ResponseEntity<?> delete(@PathVariable Long id);

    @Operation(summary = "Updating Category", description = "Updating Category", tags = {"Category"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200")})
    ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryCreateDTO categoryCreateDTO);
}
