package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePerServiceDto {
	
	private Long id;
	private Double price;
	private ServiceDto service;
	private OperationalSystemDto operationalSystem;
	
}
