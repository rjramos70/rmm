package com.ninjaone.rmm.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputCustomer {
	
	private String name;
	private Set<Long> devices;
	private Set<Long> services;
	private String login;

}
