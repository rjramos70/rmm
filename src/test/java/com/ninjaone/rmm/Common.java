package com.ninjaone.rmm;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Common {

	@SuppressWarnings("unused")
	public static String asJson(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
