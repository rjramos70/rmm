package com.ninjaone.rmm.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmm.DataGenerator;
import com.ninjaone.rmm.dto.InputDevice;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeviceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	private InputDevice inputDeviceNew;
	private InputDevice inputDeviceNewDuplicate;
	
	@BeforeEach
	void setup() {
		inputDeviceNew = DataGenerator.getInputDeviceUpdated();
		inputDeviceNewDuplicate = DataGenerator.getInputDevice() ;
	}
	

	@Test
	void findDeviceByIdTest() throws Exception {
		String jsonResponse = "{\"id\":1,\"systemName\":\"WINDOWS_001\",\"qtd\":2,\"type\":{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}}";
		this.mockMvc.perform(get("/rmm/devices/" + DataGenerator.getDEVICE_ID()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(jsonResponse));
	}
	
	@Test
	void findDevicesTest() throws Exception {
		String jsonResponse = "[{\"id\":1,\"systemName\":\"WINDOWS_001\",\"qtd\":2,\"type\":{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}},{\"id\":2,\"systemName\":\"WINDOWS_002\",\"qtd\":1,\"type\":{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}}]";
		this.mockMvc.perform(get("/rmm/devices"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(jsonResponse));
		
	}
	
	@Test
	void createTest() throws Exception {
		String inputDeviceNewString = objectMapper.writeValueAsString(inputDeviceNew);
		String jsonResponse = "{\"id\":3,\"systemName\":\"Wnix Y\",\"qtd\":2,\"type\":{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}}";
		this.mockMvc.perform(post("/rmm/devices")
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputDeviceNewString))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(jsonResponse));
	}
	
	@Test
	void createDuplicateTest() throws Exception {
		String inputDeviceNewDuplicateString = objectMapper.writeValueAsString(inputDeviceNewDuplicate);
		String response = "Service already exist in database!";
		this.mockMvc.perform(post("/rmm/devices")
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputDeviceNewDuplicateString))
				.andDo(print())
				.andExpect(status().isNotAcceptable())
				.andExpect(content().string(containsString(response)));
	}
	
	@Test
	void deleteTest() throws Exception {
		Long deviceId = 3L;
		this.mockMvc.perform(delete("/rmm/devices/" + deviceId))
				.andDo(print())
				.andExpect(status().isOk());
		
	}
	
	@Test
	 void deleteHasDependencyTest() throws Exception {
		String response = "Sorry, but you can not delete this device id: 1 because it has dependency!";
		Long deviceId = 1L;
		this.mockMvc.perform(delete("/rmm/devices/" + deviceId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString(response)));
		
	}
	
	@Test
	void deleteIdNotExistTest() throws Exception {
		Long deviceId = 30L;
		this.mockMvc.perform(delete("/rmm/devices/" + deviceId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString(String.format("The device with ID 30 does not exist!", deviceId))));
		
	}

}
