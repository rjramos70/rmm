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

import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.ServiceDto;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.service.ServiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/rmm/services")
public class ServiceController {
	
	@Autowired
	private ServiceService serviceService;
	
	@Operation(summary = "Get a list of all services", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@GetMapping
	public List<ServiceDto> findServices() {
		return serviceService.findServices();
	}
	
	@Operation(summary = "Get a service by id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public ServiceDto findServiceById(@PathVariable("id") Long id) {
		return serviceService.findById(id);
	}
	
	@Operation(summary = "Create a new service", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceDto create(@RequestBody InputService inputService) {
		return serviceService.create(inputService);
	}

	@Operation(summary = "Update a service base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}")
	public ServiceDto update(@PathVariable("id") Long id, @RequestBody InputService inputService) {
		return serviceService.update(id, inputService);
	}
	
	@Operation(summary = "Delete a service base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		serviceService.delete(id);
	}
	
}
