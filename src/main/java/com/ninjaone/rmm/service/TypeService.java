package com.ninjaone.rmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.InputType;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class TypeService {

	@Autowired
	private ValidationLayer validationLayer;

	public List<TypeDto> findTypes() {
		return validationLayer.findAllTypesAndReturnListDto();
	}

	public TypeDto findById(Long id) {
		return validationLayer.findTypeByIdAndReturnDto(id);
	}

	public TypeDto create(InputType inputType) {
		return validationLayer.createTypeAndReturnADto(inputType);
	}

	public TypeDto update(Long id, InputType inputType) {
		return validationLayer.updateTypeAndReturnADto(id, inputType);
	}

	public void delete(Long id) {
		validationLayer.deleteTypeById(id);
	}
}
