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

import com.ninjaone.rmm.dto.DeviceDto;
import com.ninjaone.rmm.dto.InputDevice;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.service.DeviceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/rmm/devices")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	@Operation(summary = "Get a list of all devices", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@GetMapping
	public List<DeviceDto> findDevices() {
		return deviceService.findDevices();
	}
	
	@Operation(summary = "Get a device by id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public DeviceDto findDeviceById(@PathVariable("id") Long id) {
		return deviceService.findById(id);
	}
	
	@Operation(summary = "Create a new device", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DeviceDto create(@RequestBody InputDevice inputDevice) {
		return deviceService.create(inputDevice);
	}

	@Operation(summary = "Update a device base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PatchMapping("/{id}")
	public DeviceDto update(@PathVariable("id") Long id, @RequestBody InputDevice inputDevice) {
		return deviceService.update(id, inputDevice);
	}
	
	@Operation(summary = "Delete a device base on id", deprecated = false, responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		deviceService.delete(id);
	}
	
}
