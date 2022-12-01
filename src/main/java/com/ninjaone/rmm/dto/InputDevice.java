package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDevice {
	
	private String systemName;
	
	private Integer qtd;
	
	private Long typeId;
	
	private String login;

}
