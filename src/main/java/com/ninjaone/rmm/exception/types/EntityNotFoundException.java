package com.ninjaone.rmm.exception.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	
	

}
