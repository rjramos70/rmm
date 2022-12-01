package com.ninjaone.rmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputType {
	
	private Long id;
	private String description;
	private Long operationalSystemId;
	@NonNull
	private String login;

}
