package com.ninjaone.rmm.common;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateGenerator {
	
	public static Date getCurrentDate() {
		return new Date();
	}

	public static Double getDefultDouble() {
		return 0D;
	}

}
