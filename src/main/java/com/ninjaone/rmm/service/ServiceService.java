package com.ninjaone.rmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.ServiceDto;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class ServiceService {
	
	@Autowired
	private ValidationLayer validationLayer;
	
	public List<ServiceDto> findServices(){
		return validationLayer.findAllServicesAndReturnListDto();
	}

	public ServiceDto findById(Long id) {
		return validationLayer.findServiceByIsAndReturnDto(id);
	}

	public ServiceDto create(InputService inputService) {
		return validationLayer.createServiceAndReturnDto(inputService);
	}

	public ServiceDto update(Long id, InputService inputService) {
		return validationLayer.updateServiceEntityAndReturnDto(id, inputService);
	}

	public void delete(Long id) {
		validationLayer.deleteServiceById(id);
	}
	
}
