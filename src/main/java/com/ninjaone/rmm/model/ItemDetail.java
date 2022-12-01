package com.ninjaone.rmm.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String type;
	private Integer qtd;
	private Double priceUnit;
	private Double priceTotal;

}
