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

import com.ninjaone.rmm.dto.CustomerDto;
import com.ninjaone.rmm.dto.InputCustomer;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/rmm/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Operation(summary = "Get a list of all customers", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@GetMapping
	public List<CustomerDto> findCustomers() {
		return customerService.findCustomers();
	}
	
	@Operation(summary = "Gets a customer by id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public CustomerDto findCustomerById(@PathVariable("id") Long id) {
		return customerService.findById(id);
	}
	
	@Operation(summary = "Create a new customer", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDto create(@RequestBody InputCustomer inputCustomer) {
		return customerService.create(inputCustomer);
	}

	@Operation(summary = "Update a customer base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}")
	public CustomerDto update(@PathVariable("id") Long id, @RequestBody InputCustomer inputCustomer) {
		return customerService.update(id, inputCustomer);
	}
	
	@Operation(summary = "Add a device or service to a customer base on customer id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}/add")
	public CustomerDto addDeviceOrService(@PathVariable("id") Long id, @RequestBody InputCustomer inputCustomer) {
		return customerService.attachDeviceOrService(id, inputCustomer);
	}
	
	@Operation(summary = "Remove a device or service to a customer base on customer id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}/remove")
	public CustomerDto removeDeviceOrService(@PathVariable("id") Long id, @RequestBody InputCustomer inputCustomer) {
		return customerService.detachDeviceOrService(id, inputCustomer);
	}
	
	@Operation(summary = "Delete a customer base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		customerService.delete(id);
	}
	
}
