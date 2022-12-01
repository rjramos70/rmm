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
import com.ninjaone.rmm.dto.InputType;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TypeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	private InputType inputTypeNew;
	
	@BeforeEach
	void setup() {
		inputTypeNew = DataGenerator.getInputTypeForNew();
	}
	

	@Test
	void findTypeByIdTest() throws Exception {
		Long typeId = 1L;
		this.mockMvc.perform(get("/rmm/types/" + typeId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}"));
	}
	
	@Test
	void findTypesTest() throws Exception {
		String jsonResponse = "[{\"id\":1,\"description\":\"Windows Workstation\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}},{\"id\":2,\"description\":\"Windows Server\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}},{\"id\":3,\"description\":\"Windows Test\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}]";
		this.mockMvc.perform(get("/rmm/types"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(jsonResponse));
		
	}
	
	@Test
	void createTest() throws Exception {
		String inputTypeNewString = objectMapper.writeValueAsString(inputTypeNew);
		String jsonResponse = "{\"id\":4,\"description\":\"Windows Workstation XYZ\",\"operationalSystem\":{\"id\":1,\"name\":\"Windows OS\"}}";
		this.mockMvc.perform(post("/rmm/types")
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputTypeNewString))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(jsonResponse));
	}
	
	@Test
	void deleteTest() throws Exception {
		
		this.mockMvc.perform(delete("/rmm/types/" + 3))
				.andDo(print())
				.andExpect(status().isOk());
		
	}
	
	@Test
	void deleteExistDependencyTest() throws Exception {
		
		this.mockMvc.perform(delete("/rmm/types/" + 1))
				.andDo(print())
				.andExpect(status().isNotAcceptable())
				.andExpect(content().string(containsString("Sorry, but you can not delete this Type because it has dependency!")));
		
	}

}
