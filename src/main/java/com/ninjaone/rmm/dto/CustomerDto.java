package com.ninjaone.rmm.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ninjaone.rmm.model.ItemDetail;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class CustomerDto {
	
	@NonNull
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private List<DeviceDto> devices;
	@NonNull
	private List<String> services;
	
	private Set<ItemDetail> totalCostDetails;
	
	@NonNull
	private Double totalCost;
}
