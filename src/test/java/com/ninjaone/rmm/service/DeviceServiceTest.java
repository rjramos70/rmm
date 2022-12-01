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
import com.ninjaone.rmm.dto.DeviceDto;
import com.ninjaone.rmm.dto.InputDevice;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.validation.ValidationLayer;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {
	
	@Mock
	private ValidationLayer validationLayer;
	
	@InjectMocks
	private DeviceService deviceService;
	private DeviceDto deviceDto;
	private DeviceDto deviceDtoUpdated;
	private InputDevice inputDevice;
	private InputDevice inputDeviceUpdated;
	private List<DeviceDto> devices;
	
	
	@BeforeEach
	void setup() {
		deviceDto = DataGenerator.getDeviceDto();
		devices = DataGenerator.getDeviceDtoList();
		inputDevice = DataGenerator.getInputDevice();
		deviceDtoUpdated = DataGenerator.getDeviceDtoUpdated();
		inputDeviceUpdated = DataGenerator.getInputDeviceUpdated();
	}
	
	@Test
	void findOperationalSystemsTest() {
		when(validationLayer.findAllDevicesAndReturnAListOfDeviceDto()).thenReturn(devices);
		
		assertEquals(devices, deviceService.findDevices());
		assertEquals(1, deviceService.findDevices().size());
		assertEquals(deviceDto, deviceService.findDevices().get(0));
	}

	@Test
	void findByIdTest() {
		when(validationLayer.findByIdAndReturnDeviceDto(DataGenerator.getDEVICE_ID())).thenReturn(deviceDto);
		DeviceDto currentDevice = deviceService.findById(DataGenerator.getDEVICE_ID());
		
		assertEquals(deviceDto.getId(), currentDevice.getId());
		assertEquals(deviceDto.getSystemName(), currentDevice.getSystemName());
		assertEquals(deviceDto.getQtd(), currentDevice.getQtd());
		assertEquals(deviceDto.getType(), currentDevice.getType());
	}
	
	@Test
	void findByIdNullTest() {
		when(validationLayer.findByIdAndReturnDeviceDto(null)).thenThrow(new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "deviceId")));
		
		try {
			deviceService.findById(null);
		} catch (Exception e) {
			assertEquals(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "deviceId"), e.getMessage());
		}
	}
	
	@Test
	void createTest() {
		when(validationLayer.createDeviceAndReturnADeviceDto(inputDevice)).thenReturn(deviceDto);
		
		DeviceDto currentDevice = deviceService.create(inputDevice);
		
		assertEquals(deviceDto.getId(), currentDevice.getId());
		assertEquals(deviceDto.getQtd(), currentDevice.getQtd());
		assertEquals(deviceDto.getSystemName(), currentDevice.getSystemName());
		assertEquals(deviceDto.getType(), currentDevice.getType());
		
	}
	
	@Test
	void updateTest() {
		when(validationLayer.updateDeviceAndReturnADeviceDto(DataGenerator.getDEVICE_ID(), inputDeviceUpdated)).thenReturn(deviceDtoUpdated);
		
		DeviceDto currentDevice = deviceService.update(DataGenerator.getDEVICE_ID(), inputDeviceUpdated);
		
		assertEquals(deviceDtoUpdated.getId(), currentDevice.getId());
		assertEquals(deviceDtoUpdated.getQtd(), currentDevice.getQtd());
		assertEquals(deviceDtoUpdated.getSystemName(), currentDevice.getSystemName());
		assertEquals(deviceDtoUpdated.getType(), currentDevice.getType());
		
	}
	
	
	@Test
	void deleteTest() {
		doNothing().when(validationLayer).deleteDeviceById(DataGenerator.getDEVICE_ID());
		deviceService.delete(DataGenerator.getDEVICE_ID());
		
		Mockito.verify(validationLayer, times(1)).deleteDeviceById(DataGenerator.getDEVICE_ID());
		
	}
	
	
}
