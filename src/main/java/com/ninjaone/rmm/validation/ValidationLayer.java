package com.ninjaone.rmm.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjaone.rmm.common.DateGenerator;
import com.ninjaone.rmm.common.GlobalMessages;
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
import com.ninjaone.rmm.exception.types.EntityConflictException;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.mapper.EntityMapper;
import com.ninjaone.rmm.model.Customer;
import com.ninjaone.rmm.model.Device;
import com.ninjaone.rmm.model.ItemDetail;
import com.ninjaone.rmm.model.OperationalSystem;
import com.ninjaone.rmm.model.PricePerService;
import com.ninjaone.rmm.model.ServiceEntity;
import com.ninjaone.rmm.model.Type;
import com.ninjaone.rmm.repository.CustomerRepository;
import com.ninjaone.rmm.repository.DeviceRepository;
import com.ninjaone.rmm.repository.OperationalSystemRepository;
import com.ninjaone.rmm.repository.PricePerServiceRepository;
import com.ninjaone.rmm.repository.ServiceRepository;
import com.ninjaone.rmm.repository.TypeRepository;

@Component
public class ValidationLayer {

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private OperationalSystemRepository operationalSystemRepository;

	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private PricePerServiceRepository pricePerServiceRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EntityMapper mapper;
	
	// Global
	public void isLoginAvailable(String login) {
		if (login == null || login.length() == 0) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "login"));
		}
	}

	
	// OPERATIONAL SYSTEM
	public OperationalSystem checkOperationalSystem(Long opertionalSystemId) {
		if (opertionalSystemId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "operationalSystemId"));
		}
		Optional<OperationalSystem> operationalSystemById = operationalSystemRepository.findById(opertionalSystemId);
		if (!operationalSystemById.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "operational system", opertionalSystemId));
		}
		return operationalSystemById.get();
	}

	// TYPE
	public Type chechType(Long typeId) {
		if (typeId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "typeId"));
		}
		Optional<Type> type = typeRepository.findById(typeId);
		// If Yes, update
		if (!type.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "type", typeId));
		}

		return type.get();
	}
	
	public TypeDto findTypeByIdAndReturnDto(Long id) {
		return  mapper.fromTypeEntityToDto(chechType(id));
	}

	public void checkDuplicateTypeByDescription(String description) {
		if (description == null || description.length() == 0) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "description"));
		}

		Type currentType = typeRepository.findByDescription(description);

		if (currentType != null) {
			throw new EntityConflictException(String.format(GlobalMessages.OBJECT_ALREADY_EXIST_MSG, "Type"));
		}

	}
	
	public Type checkTypeById(Long typeId) {
		if (typeId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "typeId"));
		}
		Optional<Type> currentType = typeRepository.findById(typeId);
		if (!currentType.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "type", typeId));
		}
		
		return currentType.get();
	}
	
	public void deleteTypeById(Long id) {
		Type currentType = checkTypeById(id);
		checkIfTypeHasDepencency(currentType);
		typeRepository.deleteById(currentType.getId());
	}
	
	private void checkIfTypeHasDepencency(Type currentType) {
		List<Device> collectDevices = deviceRepository.findAll().stream().filter(dev -> Objects.equals(dev.getType(), currentType)).collect(Collectors.toList());
		if (!collectDevices.isEmpty()) {
			throw new EntityConflictException(String.format(GlobalMessages.OBJECT_HAS_DEPENDENCY_MSG, "Type"));
		}
		
	}

	public Type createType(InputType inputType) {
		// check if login attribute is not null
		isLoginAvailable(inputType.getLogin());
		
		// Check type by name
		checkDuplicateTypeByDescription(inputType.getDescription());
		
		// check valid OP by id
		OperationalSystem checkOperationalSystem = checkOperationalSystem(inputType.getOperationalSystemId());
		
		return saveType(mapper.fromInputTypeToEntity(inputType, checkOperationalSystem),inputType.getLogin());
	}
	
	public TypeDto createTypeAndReturnADto(InputType inputType) {
		return mapper.fromTypeEntityToDto(createType(inputType));
	}

	public Type updateType(Long id, InputType inputType) {
		// check if login attribute is not null
		isLoginAvailable(inputType.getLogin());
		
		// check if the Type exist
		Type currentType = chechType(id);
		
		// If operational system id exist
		if (inputType.getOperationalSystemId() != null) {
			currentType.setOperationalSystem(checkOperationalSystem(inputType.getOperationalSystemId()));
		}
		
		// update
		currentType = mapper.updateTypeEntity(inputType, currentType);
		
		// Save and return
		return saveType(currentType, inputType.getLogin());
	}
	
	public TypeDto updateTypeAndReturnADto(Long id, InputType inputType) {
		return mapper.fromTypeEntityToDto(updateType(id, inputType));
	}
	
	public List<Type> findAllTypes() {
		return typeRepository.findAll();
	}
	
	public List<TypeDto> findAllTypesAndReturnListDto(){
		return mapper.fromListTypeEntityToListDto(findAllTypes());
	}

	public Type saveType(Type newType, String login) {
		newType.setUpdatedBy(login);
		newType.setUpdatedOn(DateGenerator.getCurrentDate());
		return typeRepository.save(newType);
	}

	public Optional<Type> findTypeById(Long id) {
		return typeRepository.findById(id);
	}


	
	// SERVICE
	public List<ServiceEntity> findAllServices() {
		return serviceRepository.findAll();
	}
	
	public List<ServiceDto> findAllServicesAndReturnListDto(){
		return mapper.fromListServiceEntityToListDto(findAllServices());
	}
	
	public ServiceEntity checkServiceById(Long serviceId) {
		if (serviceId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "serviceId"));
		}
		
		Optional<ServiceEntity> currentService = serviceRepository.findById(serviceId);
		if (!currentService.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "service", serviceId));
		}
		
		return currentService.get();
	}

	public ServiceDto findServiceByIsAndReturnDto(Long id) {
		return mapper.fromServiceEntityToDto(checkServiceById(id));
	}
	
	public ServiceEntity createServiceEntity(InputService inputService) {
		// check if login attribute is not null
		isLoginAvailable(inputService.getLogin());
		
		// Check if the new Service already exist by description on database
		checkDuplicateDeviceBySystemName(inputService.getDescription());
		
		// Validade input device and type to create the device entity
		ServiceEntity currentService = mapper.fromInputServiceToServiceEntity(inputService);
		
		// Save service
		return saveService(currentService, inputService.getLogin());
		
	}
	
	public ServiceDto createServiceAndReturnDto(InputService inputService) {
		return mapper.fromServiceEntityToDto(createServiceEntity(inputService));
	}
	
	public ServiceEntity updateServiceEntity(Long id, InputService inputService) {
		// check if login attribute is not null
		isLoginAvailable(inputService.getLogin());
		
		// Get ServiceEntity by id
		ServiceEntity currentService = checkServiceById(id);
		
		// Update current ServiceEntity
		currentService = mapper.updateServiceEntity(inputService, currentService);
		
		return saveService(currentService, inputService.getLogin());
		
	}
	
	public ServiceDto updateServiceEntityAndReturnDto(Long id, InputService inputService) {
		return mapper.fromServiceEntityToDto(updateServiceEntity(id, inputService));
	}
	
	public ServiceEntity saveService(ServiceEntity currentService, String login) {
		currentService.setUpdatedBy(login);
		currentService.setUpdatedOn(DateGenerator.getCurrentDate());
		return serviceRepository.save(currentService);
	}

	public void deleteServiceById(Long id) {
		ServiceEntity currentService = checkServiceById(id);
		checkIfServiceHasDepencency(currentService);
		checkIfServiceHasCustomerDependencyById(currentService.getId());
		serviceRepository.deleteById(currentService.getId());

	}
	
	public void checkIfServiceHasCustomerDependencyById(Long serviceId) {
		List<ServiceEntity> collectCustomerServices = customerRepository.findAll().stream().flatMap(c -> c.getServices().stream()).filter(s -> Objects.equals(s.getId(), serviceId)).collect(Collectors.toList());
		if (!collectCustomerServices.isEmpty()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_HAS_DEPENDENCY_MSG, "service id: " + serviceId));
		}
	}
	
	private void checkIfServiceHasDepencency(ServiceEntity currentService) {
		List<PricePerService> collectPrices = pricePerServiceRepository.findAll().stream().filter(price -> Objects.equals(price.getServiceEntity(), currentService)).collect(Collectors.toList());
		if (!collectPrices.isEmpty()) {
			throw new EntityConflictException(String.format(GlobalMessages.OBJECT_HAS_DEPENDENCY_MSG, "Service"));
		}
	}
	
	public Set<ServiceEntity> findAllServicesByIdList(Set<Long> services) {
		return serviceRepository.findAllById(services).stream().collect(Collectors.toSet());
	}
	
	public Set<Long> findInvalidServiceIds(Set<Long> services) {
		if (services != null) {
			return services.stream().filter(element -> !isServiceIdValid(element)).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
	
	public boolean isServiceIdValid(Long id) {
		return serviceRepository.findAll().stream().map(ServiceEntity::getId).collect(Collectors.toSet()).contains(id);
	}
	

	// DEVICE
	public List<Device> findAllDevices() {
		return deviceRepository.findAll();
	}

	public List<DeviceDto> findAllDevicesAndReturnAListOfDeviceDto() {
		return mapper.fromListDeviceToListDto(deviceRepository.findAll());
	}
	
	public void checkDuplicateDeviceBySystemName(String description) {
		if (description == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "description"));
		}

		List<Device> collectDevices = deviceRepository.findAll().stream().filter(dev ->  Objects.equals(dev.getSystemName(), description)).collect(Collectors.toList());
		if (!collectDevices.isEmpty()) {
			throw new EntityConflictException(String.format(GlobalMessages.OBJECT_ALREADY_EXIST_MSG, "Service"));
		}
	}
	
	public DeviceDto createDeviceAndReturnADeviceDto(InputDevice inputDevice) {
		// check if login attribute is not null
		isLoginAvailable(inputDevice.getLogin());
		
		// Check if the new Device already exist by name on database
		checkDuplicateDeviceBySystemName(inputDevice.getSystemName());
		
		// Check if the type is valid
		Type currentType = chechType(inputDevice.getTypeId());
		
		// Validade input device and type to create the device entity
		Device device = mapper.fromInputDeviceToEntity(inputDevice, currentType);
		
		return mapper.fromDeviceEntityToDto(saveDevice(device, inputDevice.getLogin()));
	}
	
	public DeviceDto updateDeviceAndReturnADeviceDto(Long id, InputDevice inputDevice) {
		// check if login attribute is not null
		isLoginAvailable(inputDevice.getLogin());
		
		// Get Device by id
		Device currentDevice = checkDeviceById(id);
		
		// get Type by id
		Type currentType = chechType(inputDevice.getTypeId());
		
		// update
		Device updatedDevice = mapper.updateDeviceEntity(inputDevice, currentDevice, currentType);
		
		// save and mapper to TypeDto
		DeviceDto updatedDeviceDto = mapper.fromDeviceEntityToDto(saveDevice(updatedDevice, inputDevice.getLogin()));
		
		return updatedDeviceDto;
	}
	
	public Device saveDevice(Device device, String login) {
		device.setUpdatedBy(login);
		device.setUpdatedOn(DateGenerator.getCurrentDate());
		return deviceRepository.save(device);
	}
	
	public DeviceDto findByIdAndReturnDeviceDto(Long id) {
		return mapper.fromDeviceEntityToDto(checkDeviceById(id));
	}

	public Device checkDeviceById(Long deviceId) {
		if (deviceId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "deviceId"));
		}
		
		Optional<Device> device = deviceRepository.findById(deviceId);
		if (!device.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, GlobalMessages.DEVICE_STR, deviceId));
		}
		
		return device.get();
	}
	
	public void checkIfDeviceHasCustomerDependency(Long deviceId) {
		List<Device> collectCustomerDevices = customerRepository.findAll().stream().flatMap(cust -> cust.getDevices().stream()).filter(dev -> Objects.equals(dev.getId(), deviceId)).collect(Collectors.toList());
		System.out.println("## Device: " + deviceId + " - dependencies: " + collectCustomerDevices);
		if (!collectCustomerDevices.isEmpty()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_HAS_DEPENDENCY_MSG, "device id: " + deviceId));
		}
	}

	public void deleteDeviceById(Long id) {
		checkIfDeviceHasCustomerDependency(id);
		deviceRepository.deleteById(checkDeviceById(id).getId());
	}

	public Device findDeviceBySystemName(String systemName) {
		return deviceRepository.findBySystemName(systemName);
	}
	
	public Set<Device> findAllDevicesByIdList(Set<Long> devices) {
		return deviceRepository.findAllById(devices).stream().collect(Collectors.toSet());
	}
	
	public Set<Long> findInvalidDeviceIds(Set<Long> devices) {
		if (devices != null) {
			return devices.stream().filter(element -> !isDeviceIdValid(element)).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
	
	public boolean isDeviceIdValid(Long id) {
		return deviceRepository.findAll().stream().map(Device::getId).collect(Collectors.toSet()).contains(id);
	}
	
	public void checkDeviceAndServiceInvalidIds(Customer createdCustomer, Set<Long> invalidDeviceIds, Set<Long> invalidServiceIds) {
		String msg = "";
		if (!invalidDeviceIds.isEmpty() && !invalidServiceIds.isEmpty() && createdCustomer != null) {
			msg = String.format(GlobalMessages.DEVICE_AND_SERVICE_DO_NOT_EXIST_BUT_CLIENT_WAS_CREATED_MSG, invalidDeviceIds, invalidServiceIds, createdCustomer.getId());
		}
		
		if (!invalidDeviceIds.isEmpty() && invalidServiceIds.isEmpty() && createdCustomer != null) {
			msg = String.format(GlobalMessages.SERVICE_DO_NOT_EXIST_BUT_CLIENT_WAS_CREATED_MSG, invalidDeviceIds, createdCustomer.getId());
		}
		
		if (invalidDeviceIds.isEmpty() && !invalidServiceIds.isEmpty() && createdCustomer != null) {
			msg = String.format(GlobalMessages.SERVICE_DO_NOT_EXIST_BUT_CLIENT_WAS_CREATED_MSG, invalidServiceIds, createdCustomer.getId());
		}
		
		if (!invalidDeviceIds.isEmpty() && !invalidServiceIds.isEmpty() && createdCustomer == null) {
			msg = String.format("The id of devices %s and services %s do not exist in our database! ", invalidDeviceIds, invalidServiceIds);
		}
		
		if (msg.length() > 0) {
			throw new EntityNotFoundException(msg);
		}
	}
	
	public void checkDeviceAndServiceInvalidInsideTheCustomerAndSendThrowException(Customer currentCustomer, InputCustomer inputCustomer) {
		boolean hasDevices = false;
		boolean hasServices = false;
		String msg = "The ";

		if (inputCustomer.getDevices() != null) {
			Set<Device> collectDevices = currentCustomer.getDevices().stream().filter(device -> Objects.equals(device.getId(), inputCustomer.getDevices())).collect(Collectors.toSet());
			if (!collectDevices.isEmpty()) {
				hasDevices = true;
				msg += String.format("device(s) Id(s) %s ", inputCustomer.getDevices());
			}
		}
		
		if (inputCustomer.getServices() != null) {
			Set<ServiceEntity> collectServies = currentCustomer.getServices().stream().filter(service -> Objects.equals(service.getId(), inputCustomer.getServices())).collect(Collectors.toSet());
			if (!collectServies.isEmpty()) {
				hasServices = true;
				msg += String.format(" service(s) Id(s) %s ", inputCustomer.getServices());
			}
		}
		
		msg += String.format(" does not exist for the customer [%s] %s", currentCustomer.getId(), currentCustomer.getName());
		
		if (hasDevices || hasServices) {
			throw new EntityNotFoundException(msg);
		}
	}
	

	// PRICE PER SERVICE
	public PricePerService createPricePerService(InputPricePerService inputPricePerService) {
		// check if login attribute is not null
		isLoginAvailable(inputPricePerService.getLogin());
		
		// check if service exist
		ServiceEntity currentService = checkServiceById(inputPricePerService.getServiceId());
		
		// Verify if operational system exist
		OperationalSystem currentOperationalSystem = checkOperationalSystem(inputPricePerService.getOperationalSystemId());
		
		// Check if Price per Service is duplicate and return PricePerService
		PricePerService currentPricePerService = checkDuplicatePricePerService(currentService, currentOperationalSystem);
		
		// Update 
		currentPricePerService.setPrice(inputPricePerService.getPrice());
		currentPricePerService.setCreatedBy(inputPricePerService.getLogin());
		currentPricePerService.setCreatedOn(DateGenerator.getCurrentDate());
		currentPricePerService.setUpdatedBy(inputPricePerService.getLogin());
		currentPricePerService.setUpdatedOn(DateGenerator.getCurrentDate());
		
		return pricePerServiceRepository.save(currentPricePerService);
		
	}
	
	public PricePerService updatePricePerService(Long id, InputPricePerService inputPricePerService) {
		// check if login attribute is not null
		isLoginAvailable(inputPricePerService.getLogin());
		
		// check if PricePerService by id
		PricePerService currentPricePerService = checkPricePerServiceById(id);
		
		// Check price
		if (inputPricePerService.getPrice() != null) {
			currentPricePerService.setPrice(inputPricePerService.getPrice());
		}
		
		// check if Service by id
		if (inputPricePerService.getServiceId() != null) {
			ServiceEntity newService = checkServiceById(inputPricePerService.getServiceId());
			currentPricePerService.setServiceEntity(newService);
		}
		
		// check if Operational System by id
		if (inputPricePerService.getOperationalSystemId() != null) {
			OperationalSystem newOperationSystem = checkOperationalSystem(inputPricePerService.getOperationalSystemId());
			currentPricePerService.setOperationalSystem(newOperationSystem);
		}
		
		return savePricePerService(currentPricePerService, inputPricePerService.getLogin());
	}
	
	public PricePerServiceDto updatePricePerServiceAndReturnDto(Long id, InputPricePerService inputPricePerService) {
		return mapper.fromPricePerServiceEntityToDto(updatePricePerService(id, inputPricePerService));
	}
	
	public PricePerServiceDto createPricePerServiceAndReturnADto(InputPricePerService inputPricePerService) {
		return mapper.fromPricePerServiceEntityToDto(createPricePerService(inputPricePerService));
	}
	
	public List<PricePerService> findAllPricePerServices(){
		return pricePerServiceRepository.findAll();
	}
	
	public List<PricePerServiceDto> findAllPricePerServicesAndReturnAListOfDtos(){
		return mapper.fromListPricePerServiceEntityToListDto(findAllPricePerServices());
	}
	
	public PricePerServiceDto findByIdAndReturnADto(Long id) {
		return mapper.fromPricePerServiceEntityToDto(checkPricePerServiceById(id));
	}
	
	public PricePerService checkPricePerServiceById(Long id) {
		if (id == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "pricePerServiceId"));
		}
		Optional<PricePerService> currentPricePerService = pricePerServiceRepository.findById(id);
		if (!currentPricePerService.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "price per service", id));
		}
		return currentPricePerService.get();
	}
	
	public PricePerService checkDuplicatePricePerService(ServiceEntity currentService,
			OperationalSystem currentOperationalSystem) {
		
		PricePerService currentPricePerService = pricePerServiceRepository.findPricePerServiceByServiceIdAndOperationalSystemId(currentService, currentOperationalSystem);
		if (currentPricePerService != null) {
			throw new EntityConflictException(String.format(GlobalMessages.SORRY_BUT_YOU_CAN_NOT_CREATE_A_NEW_PRICE_PER_SERVICE_WITH_SERVICE_S_AND_OPERATIONAL_SYSTEM_S_ALREADY_EXIST_IN_THE_DATABASE_WITH_ID_S, currentService.getDescription(), currentOperationalSystem.getName(), currentPricePerService.getId()));
		}
		currentPricePerService = new PricePerService();
		currentPricePerService.setServiceEntity(currentService);
		currentPricePerService.setOperationalSystem(currentOperationalSystem);
		return currentPricePerService;
	}

	public PricePerService savePricePerService(PricePerService updatedPricePerService, String login) {
		updatedPricePerService.setUpdatedBy(login);
		updatedPricePerService.setUpdatedOn(DateGenerator.getCurrentDate());
		return pricePerServiceRepository.save(updatedPricePerService);
	}

	public void deletePricePerServiceById(Long id) {
		pricePerServiceRepository.deleteById(checkPricePerServiceById(id).getId());
	}

	// CUSTOMER
	public CustomerDto createCustomerAndReturnCustomerDto(InputCustomer inputCustomer) {
		// check if login attribute is not null
		isLoginAvailable(inputCustomer.getLogin());
		
		// Verify if customer's name, exist
		Optional<Customer> customerByName = customerRepository.findAll().stream().filter(c -> Objects.equals(c.getName(), inputCustomer.getName())).findFirst();
		if (!customerByName.isEmpty()) {
			throw new EntityConflictException(String.format(GlobalMessages.CANNOT_CREATE_WITH_MSG, "customer", "name:", "'" + customerByName.get().getName() + "'"));
		}
		
		// Create customer
		Customer newCustomer = new Customer();
		newCustomer.setName(inputCustomer.getName());
		newCustomer.setCreatedBy(inputCustomer.getLogin());
		newCustomer.setCreatedOn(DateGenerator.getCurrentDate());
		newCustomer.setTotalCost(0D);
		
		// Verify valid device and service invalid ids
		Set<Long> invalidDeviceIds = findInvalidDeviceIds(inputCustomer.getDevices());
		Set<Long> invalidServiceIds = findInvalidServiceIds(inputCustomer.getServices());
		
		// We can not create a customer with just services
		if (inputCustomer.getDevices() != null) {
			Set<Device> devices = findAllDevicesByIdList(inputCustomer.getDevices());
			newCustomer.setDevices(devices);
		}else {
			throw new EntityNotFoundException(String.format(GlobalMessages.CANNOT_CREATE_CUSTOMER_WITHOUT_DEVICES, "customer", "device"));
		}
		
		if (inputCustomer.getServices() != null) {
			Set<ServiceEntity> services = findAllServicesByIdList(inputCustomer.getServices());
			newCustomer.setServices(services);
		}
		
		newCustomer = populateLoginCreateUpdateNewCustomer(newCustomer, inputCustomer.getLogin());
		CustomerDto savedCustomer = calculateTotalCostAndSaveOrNotAndReturnCustomerDto(inputCustomer.getLogin(), newCustomer, true);
		
		checkDeviceAndServiceInvalidIds(newCustomer, invalidDeviceIds, invalidServiceIds);
		
		return savedCustomer;
	}
	
	public Customer attachDeviceOrServiceAndReturnACustomer(Long id, InputCustomer inputCustomer) {
		// check if login attribute is not null
		isLoginAvailable(inputCustomer.getLogin());
		
		// check if exist customer with respective id 
		Customer currentCustomer = checkCustomerById(id);
		
		// Attach device and/or service
		currentCustomer = addDevicesAndServicesByIdsToCustomer(inputCustomer, currentCustomer);
		
		// check if has inavlid device and service ids
		Set<Long> invalidDeviceIds = findInvalidDeviceIds(inputCustomer.getDevices());
		Set<Long> invalidServiceIds = findInvalidServiceIds(inputCustomer.getServices());
		
		// validate the ids
		checkDeviceAndServiceInvalidIds(currentCustomer, invalidDeviceIds, invalidServiceIds);
		
		return currentCustomer;
	}
	
	public Customer detachDeviceOrServiceAndReturnACustomer(Long id, InputCustomer inputCustomer) {
		// check if login attribute is not null
		isLoginAvailable(inputCustomer.getLogin());
		
		// check if exist customer with respective id 
		Customer currentCustomer = checkCustomerById(id);
		
		// check if the device and service received are valid
		checkDeviceAndServiceInvalidInsideTheCustomerAndSendThrowException(currentCustomer, inputCustomer);
		
		// detach the respectivy device and/service by id
		currentCustomer = detachDevicesAndServicesByIdsToCustomerAndSave(inputCustomer, currentCustomer);
		
		// check if has inavlid device and service ids
		Set<Long> invalidDeviceIds = findInvalidDeviceIds(inputCustomer.getDevices());
		Set<Long> invalidServiceIds = findInvalidServiceIds(inputCustomer.getServices());
		
		// validate the ids
		checkDeviceAndServiceInvalidIds(currentCustomer, invalidDeviceIds, invalidServiceIds);
		
		return currentCustomer;
	}
	
	public Collection<Customer> findAllCustomer() {
		return customerRepository.findAll();
	}
	
	public Customer checkCustomerById(Long customerId) {
		if (customerId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "customerId"));
		}
		Optional<Customer> customerEntity = customerRepository.findById(customerId);
		if (!customerEntity.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "customer", customerId));
		}
		
		return customerEntity.get();
	}
	
	public CustomerDto calculateTotalCostAndSaveOrNotAndReturnCustomerDto(String login, Customer customer, boolean toSave) {
		List<ItemDetail> details = new ArrayList<>();
		
		// If list of devices is not null, calculate devices total cost
		if (customer.getDevices() != null) {
			List<PricePerService> allPricesPerServices = pricePerServiceRepository.findAll();
			List<Device> allCustomerDevices = customer.getDevices().stream().collect(Collectors.toList());
			
			double totalDevicePrice = DateGenerator.getDefultDouble();
			double totalServicePrice = DateGenerator.getDefultDouble();
			
			for (Device device : allCustomerDevices) {
				int qtdCurrentDevice = device.getQtd();
				double priceUnitDevice = allPricesPerServices.stream().filter(price -> price.getServiceEntity().getDescription().toLowerCase().startsWith(GlobalMessages.DEVICE_STR) && price.getOperationalSystem().getId().equals(device.getType().getOperationalSystem().getId())).mapToDouble(p -> p.getPrice()).sum();
				totalDevicePrice += (priceUnitDevice * qtdCurrentDevice);
				
				details.add(createNewItemDetail(device.getId(), device.getSystemName(), device.getType().getDescription(), qtdCurrentDevice, priceUnitDevice, (priceUnitDevice * qtdCurrentDevice)));
				
				// If list of services is not null, calculate services total cost
				if (customer.getServices() != null) {
					List<ServiceEntity> allCustomerServices = customer.getServices().stream().collect(Collectors.toList());
					for (ServiceEntity currentService : allCustomerServices) {
						double priceUnitService = allPricesPerServices.stream().filter(serv -> serv.getServiceEntity().getId().equals(currentService.getId()) && serv.getOperationalSystem().getId().equals(device.getType().getOperationalSystem().getId())).mapToDouble(PricePerService::getPrice).sum();
						Optional<ItemDetail> findFirst = details.stream().filter(item -> Objects.equals(item.getId(), currentService.getId()) && Objects.equals(item.getName(), currentService.getDescription())).findFirst();
						if (findFirst.isPresent()) {
							ItemDetail itemDetail = findFirst.get();
							if (itemDetail.getPriceUnit().equals(priceUnitService)) {
								int indexOf = details.indexOf(itemDetail);
								details.get(indexOf).setQtd(details.get(indexOf).getQtd() + qtdCurrentDevice);
								details.get(indexOf).setPriceTotal(details.get(indexOf).getPriceTotal() + (priceUnitService * qtdCurrentDevice));
								
								totalServicePrice += (priceUnitService * qtdCurrentDevice);
							}else {
								details.add(createNewItemDetail(currentService.getId(), currentService.getDescription(), null, qtdCurrentDevice, priceUnitService, (priceUnitService * qtdCurrentDevice)));
								totalServicePrice += (priceUnitService * qtdCurrentDevice);
							}
						}else {
							
							details.add(createNewItemDetail(currentService.getId(), currentService.getDescription(), null, qtdCurrentDevice, priceUnitService, (priceUnitService * qtdCurrentDevice)));
							totalServicePrice += (priceUnitService * qtdCurrentDevice);
						}
					}
				}
			}
			
			customer.setTotalCost(totalDevicePrice + totalServicePrice);
		}
		
		if (toSave) {
			customer = saveCustomer(customer, login);
		}
		
		CustomerDto responseCustomer = mapper.fromCustomerEntityToDto(customer);
		responseCustomer.setTotalCostDetails(details.stream().collect(Collectors.toSet()));
		
		return responseCustomer;
		
	}
	
	private ItemDetail createNewItemDetail(Long id, String name, String type, Integer qtd, Double priceUnit, Double priceTotal) {
		return ItemDetail.builder()
				.id(id)
				.name(name)
				.type(type)
				.qtd(qtd)
				.priceUnit(priceUnit)
				.priceTotal(priceTotal).build();
	}
	
	public Customer populateLoginCreateUpdateNewCustomer(Customer customer, String login) {
		customer.setCreatedBy(login);
		customer.setCreatedOn(DateGenerator.getCurrentDate());
		customer.setUpdatedBy(login);
		customer.setUpdatedOn(DateGenerator.getCurrentDate());
		customer.setTotalCost(DateGenerator.getDefultDouble());
		
		return customer;
	}

	public Customer saveCustomer(Customer newCustomer, String login) {
		newCustomer.setUpdatedBy(login);
		newCustomer.setUpdatedOn(DateGenerator.getCurrentDate());
		
		return customerRepository.save(newCustomer);
	}


	public Customer updateCustomer(Long id, InputCustomer inputCustomer) {
		isLoginAvailable(inputCustomer.getLogin());
		
		Customer currentCustomer = checkCustomerById(id);
		
		if (!inputCustomer.getName().isBlank() || inputCustomer.getName() != null) {
			currentCustomer.setName(inputCustomer.getName());
		}
		
		if (inputCustomer.getDevices() != null) {
			Set<Device> devices = findAllDevicesByIdList(inputCustomer.getDevices()).stream().collect(Collectors.toSet());
			if (!devices.isEmpty()) {
				currentCustomer.setDevices(devices);
			}
		}
		
		if (inputCustomer.getServices() != null) {
			Set<ServiceEntity> serviceEntities = findAllServicesByIdList(inputCustomer.getServices()).stream().collect(Collectors.toSet());
			if (!serviceEntities.isEmpty()) {
				currentCustomer.setServices(serviceEntities);
			}
		}
		
		Set<Long> invalidDeviceIds = findInvalidDeviceIds(inputCustomer.getDevices());
		Set<Long> invalidServiceIds = findInvalidServiceIds(inputCustomer.getServices());
		
		checkDeviceAndServiceInvalidIds(currentCustomer, invalidDeviceIds, invalidServiceIds);
		
		return currentCustomer;
		
	}

	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(checkCustomerById(id).getId());
	}
	
	public Customer addDevicesAndServicesByIdsToCustomer(InputCustomer inputCustomer,
			Customer currentCustomer) {
		
		// Checks if Device list attribute is different from null
		if (inputCustomer.getDevices() != null) {
			for (Long deviceId : inputCustomer.getDevices()) {
				Optional<Device> newDevice = deviceRepository.findById(deviceId);
				if (newDevice.isPresent()) {
					currentCustomer.getDevices().add(newDevice.get());
				}
			}
		}
		
		// Checks if Service list attribute is different from null
		if (inputCustomer.getServices() != null) {
			for (Long serviceId : inputCustomer.getServices()) {
				Optional<ServiceEntity> newService = serviceRepository.findById(serviceId);
				if (newService.isPresent()) {
					currentCustomer.getServices().add(newService.get());
				}
			}
		}
		
		currentCustomer.setUpdatedBy(inputCustomer.getLogin());
		currentCustomer.setUpdatedOn(DateGenerator.getCurrentDate());
		
		return currentCustomer;
	}
	
	public Customer detachDevicesAndServicesByIdsToCustomerAndSave(InputCustomer inputCustomer,
			Customer currentCustomer) {
		
		// Detach devices
		if (inputCustomer.getDevices() != null) {
			Set<Device> collectDevice = currentCustomer.getDevices().stream().filter(dev -> inputCustomer.getDevices().contains(dev.getId())).collect(Collectors.toSet());
			currentCustomer.detachDeviceBySet(collectDevice);
		}
		
		// Detach services
		if (inputCustomer.getServices() != null) {
			Set<ServiceEntity> collectService = currentCustomer.getServices().stream().filter(dev -> inputCustomer.getServices().contains(dev.getId())).collect(Collectors.toSet());
			currentCustomer.detachServiceBySet(collectService);
		}
		
		currentCustomer.setUpdatedBy(inputCustomer.getLogin());
		currentCustomer.setUpdatedOn(DateGenerator.getCurrentDate());
		
		return saveCustomer(currentCustomer, inputCustomer.getLogin());
	}
	

	// OPERATIONAL SYSTEM
	public List<OperationalSystem> findAllOperationalSystems() {
		return operationalSystemRepository.findAll();
	}
	
	public List<OperationalSystemDto> findAllOperationalSystemsAndReturnAListOfDtos() {
		return mapper.fromListOperationalSystemToListDto(findAllOperationalSystems());
	}
	
	public void checkDuplicateOperationalSystemByName(String name) {
		if (name == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "name"));
		}
		Optional<OperationalSystem> currentOperationSystem = operationalSystemRepository.findByName(name);
		
		if (currentOperationSystem.isPresent()) {
			throw new EntityConflictException(String.format(GlobalMessages.CANNOT_CREATE_WITH_MSG, "new operational", "name", name));
		}
	}
	
	public void checkOperationalSystemAttributeNull(InputOperationalSystem inputOperationalSystem) {
		if (inputOperationalSystem == null || inputOperationalSystem.getName().isEmpty() || inputOperationalSystem.getName() == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "inputOperationalSystem"));
		}
	}

	public OperationalSystem createOperationalSystem(InputOperationalSystem inputOperationalSystem) {
		// check if login attribute is not null
		isLoginAvailable(inputOperationalSystem.getLogin());
		
		// Check if the attribute is null
		checkOperationalSystemAttributeNull(inputOperationalSystem);
		
		// Check if has duplicated OP
		checkDuplicateOperationalSystemByName(inputOperationalSystem.getName());
		
		// Create OP entity base on InputOperationalSystem and return it
		OperationalSystem currentOperationalSystem = mapper.fromInputOperationalSystemToEntity(inputOperationalSystem);
		
		return saveOperationalSystem(currentOperationalSystem, inputOperationalSystem.getLogin());
		
	}
	
	public OperationalSystemDto createOperationalSystemAndReturnADto(InputOperationalSystem inputOperationalSystem) {
		return mapper.fromOperationalSystemEntityToDto(createOperationalSystem(inputOperationalSystem));
	}
	
	public OperationalSystem saveOperationalSystem(OperationalSystem currentOperationalSystem, String login) {
		currentOperationalSystem.setUpdatedBy(login);
		currentOperationalSystem.setUpdatedOn(DateGenerator.getCurrentDate());
		return operationalSystemRepository.save(currentOperationalSystem);
	}
	
	public OperationalSystem updateOperationalSystem(Long id, InputOperationalSystem inputOperationalSystem) {
		// check if login attribute is not null
		isLoginAvailable(inputOperationalSystem.getLogin());
		
		// Get OP entity
		OperationalSystem currentOperationalSystem = checkOperationalSystemById(id);
		
		// Check if the attribute is null
		checkOperationalSystemAttributeNull(inputOperationalSystem);
		
		// Check if has duplicated OP
		if (inputOperationalSystem.getName() != null) {
			checkDuplicateOperationalSystemByName(inputOperationalSystem.getName());
			// Update entity
			currentOperationalSystem.setName(inputOperationalSystem.getName());
		}
		
		// Save updates entity
		saveOperationalSystem(currentOperationalSystem, inputOperationalSystem.getLogin());
		
		return currentOperationalSystem;
	}
	
	public OperationalSystemDto updateOperationalSystemAndReturnADto(Long id, InputOperationalSystem inputOperationalSystem) {
		return mapper.fromOperationalSystemEntityToDto(updateOperationalSystem(id, inputOperationalSystem));
	}

	public OperationalSystemDto findOperationalSystemByIdAndReturnADto(Long id) {
		return mapper.fromOperationalSystemEntityToDto(checkOperationalSystemById(id));
	}
	
	public OperationalSystem checkOperationalSystemById(Long operationalSystemId) {
		if (operationalSystemId == null) {
			throw new EntityNotFoundException(String.format(GlobalMessages.ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY, "operationalSystemId"));
		}
		Optional<OperationalSystem> currentOperationalSystem = operationalSystemRepository.findById(operationalSystemId);
		if (!currentOperationalSystem.isPresent()) {
			throw new EntityNotFoundException(String.format(GlobalMessages.OBJECT_DOES_NOT_EXIST_WITH_ID_MSG, "operational system", operationalSystemId));
		}
		
		return currentOperationalSystem.get();
	}
	
	public void deleteOperationalSystemById(Long operationalSystemId) {	
		// Extract the valid operational system
		OperationalSystem currentOperationalSystem = checkOperationalSystemById(operationalSystemId);
		
		// Check if this OP has dependencies on price per service
		List<PricePerService> currentPricePerServices = pricePerServiceRepository.findPricePerServiceByServiceIdByOperationalSystem(currentOperationalSystem);
		
		// Check if this OP has dependencies on type
		List<Type> currentTypes = typeRepository.findTypeByOperationalSystem(currentOperationalSystem);
		if ( !currentTypes.isEmpty() || !currentPricePerServices.isEmpty() ) {
			throw new EntityConflictException(String.format(GlobalMessages.OBJECT_HAS_DEPENDENCY_MSG, "OperationalSystem"));
		}
		
		// delete OP
		operationalSystemRepository.delete(currentOperationalSystem);
		
	}

















	
	
}
