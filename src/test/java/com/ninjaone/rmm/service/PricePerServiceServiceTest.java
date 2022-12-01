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
import com.ninjaone.rmm.dto.InputPricePerService;
import com.ninjaone.rmm.dto.PricePerServiceDto;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.validation.ValidationLayer;

@ExtendWith(MockitoExtension.class)
class PricePerServiceServiceTest {
	
	@Mock
	private ValidationLayer validationLayer;
	
	@InjectMocks
	private PricePerServiceService pricePerServiceService;
	
	private PricePerServiceDto pricePerServiceDto;
	private PricePerServiceDto updatedPricePerServiceDto;
	private List<PricePerServiceDto> pricePerServiceDtoList;
	private InputPricePerService inputPricePerService;
	private InputPricePerService  updateInputPricePerService;
	
	@BeforeEach
	void setup() {
		pricePerServiceDto = DataGenerator.getPricePerServiceDto();
		pricePerServiceDtoList = DataGenerator.getPricePerServiceDtoList();
		inputPricePerService = DataGenerator.getInputPricePerService();
		updateInputPricePerService = DataGenerator.getInputPricePerServiceUpdated();
		updatedPricePerServiceDto = DataGenerator.getPricePerServiceDtoUpdated();
	}
	
	@Test
	void findOperationalSystemsTest() {
		when(validationLayer.findAllPricePerServicesAndReturnAListOfDtos()).thenReturn(pricePerServiceDtoList);
		
		assertEquals(pricePerServiceDtoList, pricePerServiceService.findPricePerService());
		assertEquals(1, pricePerServiceService.findPricePerService().size());
		assertEquals(pricePerServiceDto, pricePerServiceService.findPricePerService().get(0));
	}

	@Test
	void findByIdTest() {
		when(validationLayer.findByIdAndReturnADto(DataGenerator.getPRICE_PER_SERVICE_ID())).thenReturn(pricePerServiceDto);
		PricePerServiceDto currentPricePerService = pricePerServiceService.findById(DataGenerator.getPRICE_PER_SERVICE_ID());
		
		assertEquals(pricePerServiceDto.getId(), currentPricePerService.getId());
		assertEquals(pricePerServiceDto.getPrice(), currentPricePerService.getPrice());
		assertEquals(pricePerServiceDto.getService(), currentPricePerService.getService());
		assertEquals(pricePerServiceDto.getOperationalSystem(), currentPricePerService.getOperationalSystem());
	}
	
	@Test
	void findByIdNullTest() {
		when(validationLayer.findByIdAndReturnADto(null)).thenThrow(new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "pricePerServiceId")));
		
		try {
			pricePerServiceService.findById(null);
		} catch (Exception e) {
			assertEquals(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "pricePerServiceId"), e.getMessage());
		}
	}
	
	@Test
	void createTest() {
		when(validationLayer.createPricePerServiceAndReturnADto(inputPricePerService)).thenReturn(pricePerServiceDto);
		assertEquals(pricePerServiceDto, pricePerServiceService.create(inputPricePerService));
		
	}
	
	@Test
	void updateTest() {
		when(validationLayer.updatePricePerServiceAndReturnDto(DataGenerator.getPRICE_PER_SERVICE_ID(), updateInputPricePerService)).thenReturn(updatedPricePerServiceDto);
		PricePerServiceDto currentPricePerService = pricePerServiceService.update(DataGenerator.getPRICE_PER_SERVICE_ID(), updateInputPricePerService);
		
		assertEquals(updatedPricePerServiceDto, currentPricePerService);
		
	}
	
	
	@Test
	void deleteTest() {
		doNothing().when(validationLayer).deletePricePerServiceById(DataGenerator.getPRICE_PER_SERVICE_ID());
		pricePerServiceService.delete(DataGenerator.getPRICE_PER_SERVICE_ID());
		
		Mockito.verify(validationLayer, times(1)).deletePricePerServiceById(DataGenerator.getPRICE_PER_SERVICE_ID());
		
	}
	
	
}
