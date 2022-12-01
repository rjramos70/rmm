package com.ninjaone.rmm.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ninjaone.rmm.common.DateGenerator;
import com.ninjaone.rmm.dto.CustomerDto;
import com.ninjaone.rmm.dto.DeviceDto;
import com.ninjaone.rmm.dto.InputDevice;
import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.InputType;
import com.ninjaone.rmm.dto.OperationalSystemDto;
import com.ninjaone.rmm.dto.PricePerServiceDto;
import com.ninjaone.rmm.dto.ServiceDto;
import com.ninjaone.rmm.dto.TypeDto;
import com.ninjaone.rmm.model.Customer;
import com.ninjaone.rmm.model.Device;
import com.ninjaone.rmm.model.OperationalSystem;
import com.ninjaone.rmm.model.PricePerService;
import com.ninjaone.rmm.model.ServiceEntity;
import com.ninjaone.rmm.model.Type;

@Component
public class EntityMapper {
	
	public TypeDto fromTypeEntityToDto(Type type) {
		return new TypeDto(type.getId(), type.getDescription(), fromOperationalSystemEntityToDto(type.getOperationalSystem()));
	}
	
	public List<TypeDto> fromListTypeEntityToListDto(List<Type> typeList){
		return typeList.stream().map(this::fromTypeEntityToDto).collect(Collectors.toList());
	}

	public Type fromInputTypeToEntity(InputType inputType, OperationalSystem operationalSystem) {
		Type type = new Type();
		type.setDescription(inputType.getDescription());
		type.setOperationalSystem(operationalSystem);
		type.setCreatedBy(inputType.getLogin());
		type.setCreatedOn(DateGenerator.getCurrentDate());
		return type;
	}

	public Type updateTypeEntity(InputType inputType, Type type) {
		type.setDescription(inputType.getDescription());
		return type;
	}
	
	public DeviceDto fromDeviceEntityToDto(Device device) {
		return new DeviceDto(device.getId(), device.getSystemName(), device.getQtd(), fromTypeEntityToDto(device.getType()));
	}
	
	public List<DeviceDto> fromListDeviceToListDto(List<Device> devices){
		return devices.stream().map(this::fromDeviceEntityToDto).collect(Collectors.toList());
	}

	public Device fromInputDeviceToEntity(InputDevice inputDevice, Type type) {
		Device newDevice = new Device();
		newDevice.setSystemName(inputDevice.getSystemName());
		newDevice.setQtd(inputDevice.getQtd());
		newDevice.setType(type);
		newDevice.setCreatedBy(inputDevice.getLogin());
		newDevice.setCreatedOn(DateGenerator.getCurrentDate());
		
		return newDevice;
	}

	public Device updateDeviceEntity(InputDevice inputDevice, Device device, Type type) {
		device.setSystemName(inputDevice.getSystemName());
		device.setQtd(inputDevice.getQtd());
		device.setType(type);
		return device;
	}

	// Service
	public ServiceDto fromServiceEntityToDto(ServiceEntity service) {
		ServiceDto newServiceDto = new ServiceDto();
		newServiceDto.setId(service.getId());
		newServiceDto.setDescription(service.getDescription());
		return newServiceDto;
	}
	
	public List<ServiceDto> fromListServiceEntityToListDto(List<ServiceEntity> services){
		return services.stream().map(this::fromServiceEntityToDto).collect(Collectors.toList());
	}
	
	public ServiceEntity fromInputServiceToServiceEntity(InputService inputService) {
		ServiceEntity serviceEntity = new ServiceEntity();
		serviceEntity.setDescription(inputService.getDescription());
		serviceEntity.setCreatedBy(inputService.getLogin());
		serviceEntity.setCreatedOn(DateGenerator.getCurrentDate());
		serviceEntity.setUpdatedBy(inputService.getLogin());
		serviceEntity.setUpdatedOn(DateGenerator.getCurrentDate());
		return serviceEntity;
	}

	public ServiceEntity updateServiceEntity(InputService inputService, ServiceEntity currentService) {
		currentService.setDescription(inputService.getDescription());
		return currentService;
	}
	
	// Customer
	public CustomerDto fromCustomerEntityToDto(Customer customer) {
		CustomerDto updatedCustomer = new CustomerDto();
		updatedCustomer.setId(customer.getId());
		updatedCustomer.setName(customer.getName());
		
		if (customer.getDevices() != null) {
			List<Device> devices = customer.getDevices().stream().collect(Collectors.toList());
			updatedCustomer.setDevices(fromListDeviceToListDto(devices));
		}
		
		if (customer.getServices() != null) {
			List<String> servicesString = customer.getServices().stream().map(ServiceEntity::getDescription).distinct().collect(Collectors.toList());
			updatedCustomer.setServices(servicesString);
		}
		
		updatedCustomer.setTotalCost(customer.getTotalCost());
		
		return updatedCustomer;
	}
	
	public List<CustomerDto> fromListCustomerToListDto(List<Customer> customers){
		return customers.stream().map(this::fromCustomerEntityToDto).collect(Collectors.toList());
	}
	
	// PricePerService
	public PricePerServiceDto fromPricePerServiceEntityToDto(PricePerService pricePerService) {
		return new PricePerServiceDto(pricePerService.getId(), pricePerService.getPrice(), fromServiceEntityToDto(pricePerService.getServiceEntity()), fromOperationalSystemEntityToDto(pricePerService.getOperationalSystem()) );
	}
	
	public List<PricePerServiceDto> fromListPricePerServiceEntityToListDto(List<PricePerService> pricePerServices){
		return pricePerServices.stream().map(this::fromPricePerServiceEntityToDto).collect(Collectors.toList());
	}

	// Operational System
	public OperationalSystemDto fromOperationalSystemEntityToDto(OperationalSystem operationalSystem) {
		return new OperationalSystemDto(operationalSystem.getId(), operationalSystem.getName());
	}
	
	public List<OperationalSystemDto> fromListOperationalSystemToListDto(List<OperationalSystem> operationalSystems){
		return operationalSystems.stream().map(this::fromOperationalSystemEntityToDto).collect(Collectors.toList());
	}
	
	public OperationalSystem fromInputOperationalSystemToEntity(InputOperationalSystem inputOperationalSystem) {
		OperationalSystem currentOperationalSystem = new OperationalSystem();
		currentOperationalSystem.setName(inputOperationalSystem.getName());
		currentOperationalSystem.setCreatedBy(inputOperationalSystem.getLogin());
		currentOperationalSystem.setCreatedOn(DateGenerator.getCurrentDate());
		
		return currentOperationalSystem;
	}
}
