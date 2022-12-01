package com.ninjaone.rmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.DeviceDto;
import com.ninjaone.rmm.dto.InputDevice;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class DeviceService {
	
	@Autowired
	private ValidationLayer validationLayer;
	
	public List<DeviceDto> findDevices(){
		return validationLayer.findAllDevicesAndReturnAListOfDeviceDto();
	}

	public DeviceDto findById(Long id) {
		return validationLayer.findByIdAndReturnDeviceDto(id);
	}

	public DeviceDto create(InputDevice inputDevice) {
		return validationLayer.createDeviceAndReturnADeviceDto(inputDevice);
	}

	public DeviceDto update(Long id, InputDevice inputDevice) {
		return validationLayer.updateDeviceAndReturnADeviceDto(id, inputDevice);
	}

	public void delete(Long id) {
		validationLayer.deleteDeviceById(id);
	}
	
	
}
