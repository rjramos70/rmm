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
import com.ninjaone.rmm.dto.InputPricePerService;
import com.ninjaone.rmm.dto.PricePerServiceDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RmmApplication.class)
@AutoConfigureMockMvc
class PricePerServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PricePerServiceController pricePerServiceController;
	
	private PricePerServiceDto pricePerServiceDto;
	
	@BeforeEach
	void setup() {
		pricePerServiceDto = DataGenerator.getPricePerServiceDto();
	}
	
	
	@Test
	void testGetAllPricePerService() throws Exception {
		List<PricePerServiceDto> prices = new ArrayList<>();
		prices.add(pricePerServiceDto);

		given(pricePerServiceController.findPricePerServices()).willReturn(prices);

		mockMvc.perform(get("/rmm/price-per-services")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].price", is(pricePerServiceDto.getPrice())));
	}
	
	@Test
	void testGetPricePerService() throws Exception {
		given(pricePerServiceController.findPricePerServiceById(1L)).willReturn(pricePerServiceDto);

		mockMvc.perform(get("/rmm/price-per-services/" + pricePerServiceDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("price", is(pricePerServiceDto.getPrice())));
	}

	@Test
	void testDeletePricePerService() throws Exception {
		doNothing().when(pricePerServiceController).delete(1L);

		mockMvc.perform(delete("/rmm/price-per-services/" + pricePerServiceDto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void testAddPricePerService() throws Exception {
		InputPricePerService inputPricePerService = new InputPricePerService(22.55, 1L, 1L, "renato");

		String pricePerServiceDtoString = Common.asJson(pricePerServiceDto);
		String inputPricePerServiceString = Common.asJson(inputPricePerService);

		when(pricePerServiceController.create(inputPricePerService)).thenReturn(pricePerServiceDto);

		mockMvc.perform(post("/rmm/price-per-services")
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputPricePerServiceString))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(content().string(pricePerServiceDtoString));

	}
	
	@Test
	void testUpdatePricePerService() throws Exception {
		InputPricePerService inputPricePerService = new InputPricePerService(22.55, 1L, 1L, "renato");
		
		String pricePerServiceDtoString = Common.asJson(pricePerServiceDto);
		String inputPricePerServiceString = Common.asJson(inputPricePerService);

		when(pricePerServiceController.update(1L, inputPricePerService)).thenReturn(pricePerServiceDto);

		mockMvc.perform(patch("/rmm/price-per-services/" + pricePerServiceDto.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputPricePerServiceString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(pricePerServiceDtoString));

	}
	
}
