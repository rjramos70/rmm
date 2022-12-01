package com.ninjaone.rmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.OperationalSystemDto;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class OperationalSystemService {
	
	@Autowired
	private ValidationLayer validationLayer;
	
	public List<OperationalSystemDto> findOperationalSystems(){
		return validationLayer.findAllOperationalSystemsAndReturnAListOfDtos();
	}

	public OperationalSystemDto findById(Long id) {
		return validationLayer.findOperationalSystemByIdAndReturnADto(id);
	}

	public OperationalSystemDto create(InputOperationalSystem inputOperationalSystem) {
		return validationLayer.createOperationalSystemAndReturnADto(inputOperationalSystem);
	}

	public OperationalSystemDto update(Long id, InputOperationalSystem inputOperationalSystem) {
		return validationLayer.updateOperationalSystemAndReturnADto(id, inputOperationalSystem);
	}

	public void delete(Long id) {
		validationLayer.deleteOperationalSystemById(id);
	}
	
	
}
