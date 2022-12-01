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
import com.ninjaone.rmm.dto.InputOperationalSystem;
import com.ninjaone.rmm.dto.OperationalSystemDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OperationalSystemControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	private OperationalSystemDto operationalSystemDto;
	private InputOperationalSystem inputOperationalSystemUpdated;
	
	@BeforeEach
	void setup() {
		operationalSystemDto = DataGenerator.getOperationalSystemDto();
		inputOperationalSystemUpdated = DataGenerator.getInputOperationalSystemForNew();
	}
	

	@Test
	void findDeviceByIdTest() throws Exception {
		this.mockMvc.perform(get("/rmm/ops/" + DataGenerator.getOP_ID()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(operationalSystemDto)));
	}
	
	@Test
	void findOperationalSystemsTest() throws Exception {
		String jsonResponse = "[{\"id\":1,\"name\":\"Windows OS\"},{\"id\":2,\"name\":\"Mac Test\"},{\"id\":4,\"name\":\"Windows XYZ\"}]";
		this.mockMvc.perform(get("/rmm/ops"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(jsonResponse));
	}
	
	@Test
	void createTest() throws Exception {
		String inputOperationalSystemUpdatedString = objectMapper.writeValueAsString(inputOperationalSystemUpdated);
		String jsonResponse = "{\"id\":4,\"name\":\"Windows XYZ\"}";
		this.mockMvc.perform(post("/rmm/ops")
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputOperationalSystemUpdatedString))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(jsonResponse));
	}
	
	@Test
	void deleteTest() throws Exception {
		Long opId = 3L;
		this.mockMvc.perform(delete("/rmm/ops/" + opId))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteNotExistTest() throws Exception {
		Long opId = 10L;
		this.mockMvc.perform(delete("/rmm/ops/" + opId))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString(String.format("The operational system with ID %s does not exist!", opId))));
	}

}
