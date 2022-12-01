package com.ninjaone.rmm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaone.rmm.dto.CustomerDto;
import com.ninjaone.rmm.dto.InputCustomer;
import com.ninjaone.rmm.model.Customer;
import com.ninjaone.rmm.validation.ValidationLayer;

@Service
public class CustomerService {
	
	@Autowired
	private ValidationLayer validationLayer;
	
	public List<CustomerDto> findCustomers(){
		return validationLayer.findAllCustomer().stream().map(cust -> validationLayer.calculateTotalCostAndSaveOrNotAndReturnCustomerDto(null, cust, false)).collect(Collectors.toList());
	}

	public CustomerDto findById(Long id) {
		Customer currentCustomer = validationLayer.checkCustomerById(id);
		return validationLayer.calculateTotalCostAndSaveOrNotAndReturnCustomerDto(null, currentCustomer, false);
	}

	public CustomerDto create(InputCustomer inputCustomer) {
		return validationLayer.createCustomerAndReturnCustomerDto(inputCustomer);
	}

	public CustomerDto update(Long id, InputCustomer inputCustomer) {
		return validationLayer.calculateTotalCostAndSaveOrNotAndReturnCustomerDto(inputCustomer.getLogin(), validationLayer.updateCustomer(id, inputCustomer), true);
	}

	public void delete(Long id) {
		validationLayer.deleteCustomerById(id);
	}

	public CustomerDto attachDeviceOrService(Long id, InputCustomer inputCustomer) {
		return validationLayer.calculateTotalCostAndSaveOrNotAndReturnCustomerDto(inputCustomer.getLogin(), validationLayer.attachDeviceOrServiceAndReturnACustomer(id, inputCustomer), true);
	}
	
	public CustomerDto detachDeviceOrService(Long id, InputCustomer inputCustomer) {
		return validationLayer.calculateTotalCostAndSaveOrNotAndReturnCustomerDto(inputCustomer.getLogin(), validationLayer.detachDeviceOrServiceAndReturnACustomer(id, inputCustomer), true);
	}
	

}
