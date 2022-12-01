package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputPricePerService {
	
	private Double price;
	private Long serviceId;
	private Long operationalSystemId;
	private String login;
	

}
