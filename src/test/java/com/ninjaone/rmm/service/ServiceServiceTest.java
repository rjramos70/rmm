package com.ninjaone.rmm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.ServiceDto;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.validation.ValidationLayer;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {
	
	@Mock
	private ValidationLayer validationLayer;
	
	@InjectMocks
	private ServiceService serviceService;
	
	private ServiceDto serviceDto;
	private ServiceDto newServiceDto;
	private ServiceDto updateServiceDto;
	private InputService inputService;
	private InputService inputServiceUpdate;
	private List<ServiceDto> serviceDtoList = new ArrayList<>();
	
	
	@BeforeEach
	void setup() {
		serviceDto = DataGenerator.getServiceDto();
		updateServiceDto = DataGenerator.getServiceDtoUpdated();
		inputService = DataGenerator.getInputService();
		inputServiceUpdate = DataGenerator.getInputServiceUpdated();
		newServiceDto = DataGenerator.getServiceDtoUpdated();
		serviceDtoList = DataGenerator.getServiceDtoList();
	}
	
	@Test
	void findServicesTest() {
		when(validationLayer.findAllServicesAndReturnListDto()).thenReturn(serviceDtoList);
		
		assertEquals(serviceDtoList, serviceService.findServices());
		assertEquals(1, serviceService.findServices().size());
		assertEquals(serviceDto, serviceService.findServices().get(0));
	}

	@Test
	void findByIdTest() {
		when(validationLayer.findServiceByIsAndReturnDto(DataGenerator.getSERVICE_ID())).thenReturn(serviceDto);
		ServiceDto currentServiceDto = serviceService.findById(DataGenerator.getSERVICE_ID());
		
		assertEquals(serviceDto.getId(), currentServiceDto.getId());
		assertEquals(serviceDto.getDescription(), currentServiceDto.getDescription());
	}
	
	@Test
	void findByIdNullTest() {
		when(validationLayer.findServiceByIsAndReturnDto(null)).thenThrow(new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "serviceId")));
		
		try {
			serviceService.findById(null);
		} catch (Exception e) {
			assertEquals(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "serviceId"), e.getMessage());
		}
	}

	@Test
	void createTest() {
		when(validationLayer.createServiceAndReturnDto(inputService)).thenReturn(newServiceDto);
		assertEquals(newServiceDto, serviceService.create(inputService));
		
	}
	
	@Test
	void updateTest() {
		when(validationLayer.updateServiceEntityAndReturnDto(DataGenerator.getSERVICE_ID(), inputServiceUpdate)).thenReturn(updateServiceDto);
		ServiceDto currentUpdatedService = serviceService.update(DataGenerator.getSERVICE_ID(), inputServiceUpdate);
		
		assertEquals(updateServiceDto, currentUpdatedService);
	}
	
	
	@Test
	void deleteTest() {
		doNothing().when(validationLayer).deleteServiceById(DataGenerator.getSERVICE_ID());
		serviceService.delete(DataGenerator.getSERVICE_ID());
		
		Mockito.verify(validationLayer, times(1)).deleteServiceById(DataGenerator.getSERVICE_ID());
	}
	
}
