package com.ninjaone.rmm.exception.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationError {

	private final String field;
	private final String message;

}
