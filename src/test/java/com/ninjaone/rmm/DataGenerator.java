package com.ninjaone.rmm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ninjaone.rmm.dto.CustomerDto;
import com.ninjaone.rmm.dto.DeviceDto;
import com.ninjaone.rmm.dto.InputCustomer;
import com.ninjaone.rmm.dto.InputDevice;
import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.InputPricePerService;
import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.InputType;
import com.ninjaone.rmm.dto.OperationalSystemDto;
import com.ninjaone.rmm.dto.PricePerServiceDto;
import com.ninjaone.rmm.dto.ServiceDto;
import com.ninjaone.rmm.dto.TypeDto;

public class DataGenerator {

	
	// TYPE
	private static final Long TYPE_ID = 1L;
	private static final String TYPE_DESCRIPTION = "Windows Workstation 2";
	private static final String UPDATED_TYPE_DESCRIPTION = "Windows Workstation 234";
	private static final String NEW_TYPE_DESCRIPTION = "Windows Workstation XYZ";

	// OPERATIONAL SYSTEM
	private static final Long OP_ID = 1L;
	private static final Long OP_ID_NEW = 3L;
	private static final String OP_NAME = "Windows OS";
	private static final String UPDATED_OP_NAME = "Windows OS 2";
	private static final String NEW_OP_NAME = "Windows XYZ";

	// PRICE PER SERVICE
	private static final Long PRICE_PER_SERVICE_ID = 1L;

	// DEVICE
	private static final Long DEVICE_ID = 1L;
	private static final String DEVICE_SYSTEMNAME = "WINDOWS_001";
	private static final String UPDATED_DEVICE_SYSTEMNAME = "Wnix Y";
	private static final Set<Long> SET_OF_DEVICES = Stream.of(1L, 2L).collect(Collectors.toSet());

	// SERVICE
	private static final Long SERVICE_ID = 1L;
	private static final Long SERVICE_ID_NEW = 6L;
	private static final String SERVICE_DESCRIPTION = "Malware Protection";
	private static final String UPDATED_SERVICE_DESCRIPTION = "Full Backdoor Protection";
	private static final String NEW_SERVICE_DESCRIPTION = "Full Backdoor Protection XPT";
	private static final Set<Long> SET_OF_SERVICES = Stream.of(1L, 2L).collect(Collectors.toSet());
	
	// CUSTOMER
	private static final Long CUSTOMER_ID = 1L;
	private static final String CUSTOMER_NEW_NAME = "New custormer name";
	private static final String CUSTOMER_NAME_ALREADY_EXIST = "Marco Aurelio";
	

	private static final String LOGIN = "renato";

	
	// Customer
	public static Long getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	
	public static CustomerDto getCustomerDto() {
		return new CustomerDto(CUSTOMER_ID, CUSTOMER_NEW_NAME, getDeviceDtoList(), getServiceDtoStringList(), 0D);
	}
	
	public static InputCustomer getInputCustomerNew() {
		InputCustomer customer = new InputCustomer();
		customer.setName(CUSTOMER_NEW_NAME);
		customer.setDevices(SET_OF_DEVICES);
		customer.setServices(SET_OF_SERVICES);
		customer.setLogin(LOGIN);
		return customer;
	}
	
	public static InputCustomer getInputCustomerNewWithoutService() {
		InputCustomer customer = new InputCustomer();
		customer.setName(CUSTOMER_NEW_NAME);
		customer.setDevices(SET_OF_DEVICES);
		customer.setLogin(LOGIN);
		return customer;
	}
	
	public static InputCustomer getInputCustomerNewWithoutDevice() {
		InputCustomer customer = new InputCustomer();
		customer.setName(CUSTOMER_NEW_NAME);
		customer.setServices(SET_OF_SERVICES);
		customer.setLogin(LOGIN);
		return customer;
	}
	
	public static InputCustomer getInputCustomerNewDuplicate() {
		InputCustomer customer = new InputCustomer();
		customer.setName(CUSTOMER_NAME_ALREADY_EXIST);
		customer.setDevices(SET_OF_DEVICES);
		customer.setServices(SET_OF_SERVICES);
		customer.setLogin(LOGIN);
		return customer;
	}
	
	
	// Device
	public static Long getDEVICE_ID() {
		return DEVICE_ID;
	}
	
	public static DeviceDto getDeviceDto() {
		return new DeviceDto(DEVICE_ID, DEVICE_SYSTEMNAME, 2, getTypeDto());
	}
	
	public static DeviceDto getDeviceDtoUpdated() {
		return new DeviceDto(DEVICE_ID, UPDATED_DEVICE_SYSTEMNAME, 2, getTypeDto());
	}
	
	public static List<DeviceDto> getDeviceDtoList() {
		List<DeviceDto> devices = new ArrayList<>();
		devices.add(getDeviceDto());
		return devices;
	}
	
	public static InputDevice getInputDevice() {
		return new InputDevice(DEVICE_SYSTEMNAME, 1, TYPE_ID, LOGIN);
	}
	
	public static InputDevice getInputDeviceUpdated() {
		return new InputDevice(UPDATED_DEVICE_SYSTEMNAME, 2, TYPE_ID, LOGIN);
	}
	

	

	// Price per Service
	public static Long getPRICE_PER_SERVICE_ID() {
		return PRICE_PER_SERVICE_ID;
	}

	public static PricePerServiceDto getPricePerServiceDto() {
		return new PricePerServiceDto(PRICE_PER_SERVICE_ID, 13.10, getServiceDto(), getOperationalSystemDto());
	}

	public static PricePerServiceDto getPricePerServiceDtoUpdated() {
		return new PricePerServiceDto(PRICE_PER_SERVICE_ID, 18.32, getServiceDto(), getOperationalSystemDto());
	}

	public static List<PricePerServiceDto> getPricePerServiceDtoList() {
		List<PricePerServiceDto> prices = new ArrayList<>();
		prices.add(getPricePerServiceDto());
		return prices;
	}

	public static InputPricePerService getInputPricePerService() {
		return new InputPricePerService(13.10, SERVICE_ID, OP_ID, LOGIN);
	}

	public static InputPricePerService getInputPricePerServiceUpdated() {
		return new InputPricePerService(18.32, SERVICE_ID, OP_ID, LOGIN);
	}
	
	public static InputPricePerService getInputPricePerServiceForNew() {
		return new InputPricePerService(21.13, SERVICE_ID_NEW, OP_ID_NEW, LOGIN);
	}
	
	public static InputPricePerService getInputPricePerServiceForNewDuplicate() {
		return new InputPricePerService(33.33, SERVICE_ID, OP_ID, LOGIN);
	}

	// Service
	public static Long getSERVICE_ID() {
		return SERVICE_ID;
	}

	public static ServiceDto getServiceDto() {
		return new ServiceDto(SERVICE_ID, SERVICE_DESCRIPTION);
	}

	public static ServiceDto getServiceDtoUpdated() {
		return new ServiceDto(SERVICE_ID, UPDATED_SERVICE_DESCRIPTION);
	}
	
	public static InputService getInputService() {
		return new InputService(SERVICE_DESCRIPTION, LOGIN);
	}

	public static InputService getInputServiceUpdated() {
		return new InputService(UPDATED_SERVICE_DESCRIPTION, LOGIN);
	}
	
	public static InputService getInputServiceNew() {
		return new InputService(NEW_SERVICE_DESCRIPTION, LOGIN);
	}

	public static List<ServiceDto> getServiceDtoList() {
		List<ServiceDto> services = new ArrayList<>();
		services.add(getServiceDto());
		return services;
	}
	
	public static List<String> getServiceDtoStringList() {
		List<String> services = new ArrayList<>();
		services.add(getServiceDto().getDescription());
		return services;
	}

	// Operational System
	public static Long getOP_ID() {
		return OP_ID;
	}

	public static OperationalSystemDto getOperationalSystemDto() {
		return new OperationalSystemDto(OP_ID, OP_NAME);
	}

	public static OperationalSystemDto getOperationalSystemDtoUpdated() {
		return new OperationalSystemDto(OP_ID, UPDATED_OP_NAME);
	}

	public static InputOperationalSystem getInputOperationalSystem() {
		return new InputOperationalSystem(OP_NAME, LOGIN);
	}

	public static InputOperationalSystem getInputOperationalSystemUpdated() {
		return new InputOperationalSystem(UPDATED_OP_NAME, LOGIN);
	}
	
	public static InputOperationalSystem getInputOperationalSystemForNew() {
		return new InputOperationalSystem(NEW_OP_NAME, LOGIN);
	}

	public static List<OperationalSystemDto> getOperationalSystemDtoList() {
		List<OperationalSystemDto> ops = new ArrayList<>();
		ops.add(getOperationalSystemDto());
		return ops;
	}

	// Type
	public static Long getTYPE_ID() {
		return TYPE_ID;
	}

	public static TypeDto getTypeDto() {
		return new TypeDto(TYPE_ID, TYPE_DESCRIPTION, getOperationalSystemDto());
	}

	public static TypeDto getTypeDtoUpdated() {
		return new TypeDto(TYPE_ID, UPDATED_TYPE_DESCRIPTION, getOperationalSystemDto());
	}

	public static InputType getInputType() {
		return new InputType(TYPE_ID, TYPE_DESCRIPTION, OP_ID, LOGIN);
	}

	public static InputType getInputTypeUpdated() {
		return new InputType(TYPE_ID, UPDATED_TYPE_DESCRIPTION, OP_ID, LOGIN);
	}
	
	public static InputType getInputTypeForNew() {
		return new InputType(TYPE_ID, NEW_TYPE_DESCRIPTION, OP_ID, LOGIN);
	}

	public static List<TypeDto> getTypeDtoList() {
		List<TypeDto> types = new ArrayList<>();
		types.add(getTypeDto());
		return types;
	}

}
