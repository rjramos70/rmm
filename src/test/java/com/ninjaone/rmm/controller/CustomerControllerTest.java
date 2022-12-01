package com.ninjaone.rmm.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ninjaone.rmm.Common;
import com.ninjaone.rmm.DataGenerator;
import com.ninjaone.rmm.RmmApplication;
import com.ninjaone.rmm.dto.CustomerDto;
import com.ninjaone.rmm.dto.InputCustomer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RmmApplication.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerController customerController;

	private CustomerDto customerDto;

	@BeforeEach
	void setup() {
		customerDto = DataGenerator.getCustomerDto();
	}

	@Test
	void testGetAllCustomers() throws Exception {
		List<CustomerDto> customers = new ArrayList<>();
		customers.add(customerDto);

		given(customerController.findCustomers()).willReturn(customers);

		mockMvc.perform(get("/rmm/customers").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is(customerDto.getName())));
	}

	@Test
	void testGetCustomer() throws Exception {
		given(customerController.findCustomerById(1L)).willReturn(customerDto);

		mockMvc.perform(get("/rmm/customers/" + customerDto.getId()).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("name", is(customerDto.getName())));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		doNothing().when(customerController).delete(1L);

		mockMvc.perform(delete("/rmm/customers/" + customerDto.getId()).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void testAddCustomer() throws Exception {
		InputCustomer inputCustomer = new InputCustomer("New custormer name", null, null, "renato");

		String customerDtoString = Common.asJson(customerDto);
		String inputCustomerString = Common.asJson(inputCustomer);

		when(customerController.create(inputCustomer)).thenReturn(customerDto);

		mockMvc.perform(post("/rmm/customers").contentType(MediaType.APPLICATION_JSON).content(inputCustomerString))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().string(customerDtoString));

	}
	
	@Test
	void testUpdateCustomer() throws Exception {
		InputCustomer inputCustomer = new InputCustomer("New custormer name 22", null, null, "renato");
		
		String customerDtoString = Common.asJson(customerDto);
		String inputCustomerString = Common.asJson(inputCustomer);

		when(customerController.update(1L, inputCustomer)).thenReturn(customerDto);

		mockMvc.perform(patch("/rmm/customers/" + customerDto.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputCustomerString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(customerDtoString));
	}

}
