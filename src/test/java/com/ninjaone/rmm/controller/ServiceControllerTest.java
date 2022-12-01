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
import com.ninjaone.rmm.dto.InputService;
import com.ninjaone.rmm.dto.ServiceDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RmmApplication.class)
@AutoConfigureMockMvc
class ServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ServiceController serviceController;
	
	private ServiceDto serviceDto;
	
	@BeforeEach
	void setup() {
		serviceDto = DataGenerator.getServiceDto();
	}
	
	@Test
	void testGetAllServices() throws Exception {
		List<ServiceDto> services = new ArrayList<>();
		services.add(serviceDto);

		given(serviceController.findServices()).willReturn(services);

		mockMvc.perform(get("/rmm/services")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].description", is(serviceDto.getDescription())));
	}
	
	@Test
	void testGetService() throws Exception {
		given(serviceController.findServiceById(1L)).willReturn(serviceDto);

		mockMvc.perform(get("/rmm/services/" + serviceDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("description", is(serviceDto.getDescription())));
	}
	
	@Test
	void testDeleteService() throws Exception {
		doNothing().when(serviceController).delete(1L);

		mockMvc.perform(delete("/rmm/services/" + serviceDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	void testAddService() throws Exception {
		InputService inputService = new InputService("New Service name", "renato");
		
		String serviceDtoString = Common.asJson(serviceDto);
		String inputServiceString = Common.asJson(inputService);

		when(serviceController.create(inputService)).thenReturn(serviceDto);

		mockMvc.perform(post("/rmm/services")
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputServiceString))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().string(serviceDtoString));

	}
	
	@Test
	void testUpdateService() throws Exception {
		InputService inputService = new InputService("New Service name", "renato");
		
		String serviceDtoString = Common.asJson(serviceDto);
		String inputServiceString = Common.asJson(inputService);

		when(serviceController.update(1L, inputService)).thenReturn(serviceDto);

		mockMvc.perform(patch("/rmm/services/" + serviceDto.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputServiceString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(serviceDtoString));

	}

}
