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
import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.OperationalSystemDto;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.validation.ValidationLayer;

@ExtendWith(MockitoExtension.class)
class OperationalSystemServiceTest {
	
	@Mock
	private ValidationLayer validationLayer;
	
	@InjectMocks
	private OperationalSystemService operationalSystemService;
	
	private OperationalSystemDto operationalSystemDto;
	private OperationalSystemDto updatedOperationalSystemDto;
	private InputOperationalSystem inputOperationalSystem;
	private List<OperationalSystemDto> operationalSystemDtoList;
	
	
	@BeforeEach
	void setup() {
		operationalSystemDto = DataGenerator.getOperationalSystemDto();
		updatedOperationalSystemDto = DataGenerator.getOperationalSystemDtoUpdated();
		inputOperationalSystem = DataGenerator.getInputOperationalSystem();
		operationalSystemDtoList = DataGenerator.getOperationalSystemDtoList();
	}
	
	@Test
	void findOperationalSystemsTest() {
		when(validationLayer.findAllOperationalSystemsAndReturnAListOfDtos()).thenReturn(operationalSystemDtoList);
		
		assertEquals(operationalSystemDtoList, operationalSystemService.findOperationalSystems());
		assertEquals(1, operationalSystemService.findOperationalSystems().size());
		assertEquals(operationalSystemDto, operationalSystemService.findOperationalSystems().get(0));
	}

	@Test
	void findByIdTest() {
		when(validationLayer.findOperationalSystemByIdAndReturnADto(DataGenerator.getOP_ID())).thenReturn(operationalSystemDto);
		OperationalSystemDto currentOperationalSystem = operationalSystemService.findById(DataGenerator.getOP_ID());
		
		assertEquals(operationalSystemDto.getId(), currentOperationalSystem.getId());
		assertEquals(operationalSystemDto.getName(), currentOperationalSystem.getName());
	}
	
	@Test
	void findByIdNullTest() {
		when(validationLayer.findOperationalSystemByIdAndReturnADto(null)).thenThrow(new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "operationalSystemId")));
		
		try {
			operationalSystemService.findById(null);
		} catch (Exception e) {
			assertEquals(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "operationalSystemId"), e.getMessage());
		}
	}
	
	@Test
	void createTest() {
		when(validationLayer.createOperationalSystemAndReturnADto(inputOperationalSystem)).thenReturn(operationalSystemDto);
		assertEquals(operationalSystemDto, operationalSystemService.create(inputOperationalSystem));
		
	}
	
	@Test
	void updateTest() {
		when(validationLayer.updateOperationalSystemAndReturnADto(DataGenerator.getOP_ID(), inputOperationalSystem)).thenReturn(updatedOperationalSystemDto);
		OperationalSystemDto currentUpdateOP = operationalSystemService.update(DataGenerator.getOP_ID(), inputOperationalSystem);
		
		assertEquals(updatedOperationalSystemDto, currentUpdateOP);
		
	}
	
	
	@Test
	void deleteTest() {
		doNothing().when(validationLayer).deleteOperationalSystemById(DataGenerator.getOP_ID());
		operationalSystemService.delete(DataGenerator.getOP_ID());
		
		Mockito.verify(validationLayer, times(1)).deleteOperationalSystemById(DataGenerator.getOP_ID());
		
	}
	
	
}
