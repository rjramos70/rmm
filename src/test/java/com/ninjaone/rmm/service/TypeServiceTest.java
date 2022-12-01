package com.ninjaone.rmm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ninjaone.rmm.DataGenerator;
import com.ninjaone.rmm.common.GlobalMessages;
import com.ninjaone.rmm.dto.InputType;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.validation.ValidationLayer;

@ExtendWith(MockitoExtension.class)
class TypeServiceTest {
	
	@Mock
	private ValidationLayer validationLayer;
	
	@InjectMocks
	private TypeService typeService;
	
	private TypeDto typeDto;
	private TypeDto updateTypeDto;
	private InputType inputType;
	private List<TypeDto> typeDtoList;
	
	
	@BeforeEach
	void setup() {
		typeDto = DataGenerator.getTypeDto();
		updateTypeDto = DataGenerator.getTypeDtoUpdated();
		inputType = DataGenerator.getInputType();
		typeDtoList = DataGenerator.getTypeDtoList();
	}
	
	@Test
	void findTypesTest() {
		when(validationLayer.findAllTypesAndReturnListDto()).thenReturn(typeDtoList);
		
		assertEquals(typeDtoList, typeService.findTypes());
		assertEquals(1, typeService.findTypes().size());
		assertEquals(typeDto, typeService.findTypes().get(0));
	}

	@Test
	void findByIdTest() {
		when(validationLayer.findTypeByIdAndReturnDto(DataGenerator.getTYPE_ID())).thenReturn(typeDto);
		TypeDto currentTypeDto = typeService.findById(DataGenerator.getTYPE_ID());
		
		assertEquals(typeDto.getId(), currentTypeDto.getId());
		assertEquals(typeDto.getDescription(), currentTypeDto.getDescription());
		assertEquals(typeDto.getOperationalSystem(), currentTypeDto.getOperationalSystem());
	}
	
	@Test
	void findByIdNullTest() {
		when(validationLayer.findTypeByIdAndReturnDto(null)).thenThrow(new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "typeId")));
		
		try {
			typeService.findById(null);
		} catch (Exception e) {
			assertEquals(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "typeId"), e.getMessage());
		}
	}

	@Test
	void createTest() {
		when(validationLayer.createTypeAndReturnADto(inputType)).thenReturn(typeDto);
		assertEquals(typeDto, typeService.create(inputType));
		
	}
	
	@Test
	void updateTest() {
		when(validationLayer.updateTypeAndReturnADto(DataGenerator.getOP_ID(), inputType)).thenReturn(updateTypeDto);
		TypeDto currentUpdatedTypeDto = typeService.update(DataGenerator.getOP_ID(), inputType);
		
		assertEquals(updateTypeDto, currentUpdatedTypeDto);
	}
	
	
	@Test
	void deleteTest() {
		doNothing().when(validationLayer).deleteTypeById(DataGenerator.getTYPE_ID());
		typeService.delete(DataGenerator.getTYPE_ID());
		
		Mockito.verify(validationLayer, times(1)).deleteTypeById(DataGenerator.getTYPE_ID());
	}
	
	
}
