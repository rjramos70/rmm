package com.ninjaone.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmm.dto.InputType;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.service.TypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/rmm/types")
public class TypeController {

	@Autowired
	private TypeService typeService;

	@Operation(summary = "Get a list of all types", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@GetMapping
	public List<TypeDto> findTypes() {
		return typeService.findTypes();
	}

	@Operation(summary = "Get a type by id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public TypeDto findTypeById(@PathVariable("id") Long id) {
		return typeService.findById(id);
	}

	@Operation(summary = "Create a new type", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TypeDto create(@RequestBody InputType inputType) {
		return typeService.create(inputType);
	}

	@Operation(summary = "Update a type base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}")
	public TypeDto update(@PathVariable("id") Long id, @RequestBody InputType inputType) {
		return typeService.update(id, inputType);
	}

	@Operation(summary = "Delete a type base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		typeService.delete(id);
	}

}
