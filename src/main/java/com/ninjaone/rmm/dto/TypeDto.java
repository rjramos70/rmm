package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
	
	private Long id;
	private String description;
	private OperationalSystemDto operationalSystem;

}
