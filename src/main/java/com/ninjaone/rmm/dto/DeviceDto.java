package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
	
	private Long id;
	private String systemName;
	
	private Integer qtd;
	
	private TypeDto type;
	
}
