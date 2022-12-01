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

import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.OperationalSystemDto;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.service.OperationalSystemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/rmm/ops")
public class OperationalSystemController {
	
	@Autowired
	private OperationalSystemService operationalSystemService;
	
	@Operation(summary = "Get a list of all operational system", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@GetMapping
	public List<OperationalSystemDto> findOperationalSystems() {
		return operationalSystemService.findOperationalSystems();
	}
	
	@Operation(summary = "Get an operational system by id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public OperationalSystemDto findDeviceById(@PathVariable("id") Long id) {
		return operationalSystemService.findById(id);
	}
	
	@Operation(summary = "Create a new operational system", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OperationalSystemDto create(@RequestBody InputOperationalSystem inputOperationalSystem) {
		return operationalSystemService.create(inputOperationalSystem);
	}

	@Operation(summary = "Update an operational system base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}")
	public OperationalSystemDto update(@PathVariable("id") Long id, @RequestBody InputOperationalSystem inputOperationalSystem) {
		return operationalSystemService.update(id, inputOperationalSystem);
	}
	
	@Operation(summary = "Delete an operational system base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		operationalSystemService.delete(id);
	}
	
}
