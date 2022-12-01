package com.ninjaone.rmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.InputPricePerService;
import com.ninjaone.rmm.dto.PricePerServiceDto;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class PricePerServiceService {
	
	@Autowired
	private ValidationLayer validationLayer;
	
	public List<PricePerServiceDto> findPricePerService(){
		return validationLayer.findAllPricePerServicesAndReturnAListOfDtos();
	}

	public PricePerServiceDto findById(Long id) {
		return validationLayer.findByIdAndReturnADto(id);
	}

	public PricePerServiceDto create(InputPricePerService inputPricePerService) {
		return validationLayer.createPricePerServiceAndReturnADto(inputPricePerService);
	}


	public PricePerServiceDto update(Long id, InputPricePerService inputPricePerService) {
		return validationLayer.updatePricePerServiceAndReturnDto(id, inputPricePerService);
	}

	public void delete(Long id) {
		validationLayer.deletePricePerServiceById(id);
	}
	
}
