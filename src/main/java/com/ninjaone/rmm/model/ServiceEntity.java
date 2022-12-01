package com.ninjaone.rmm.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "ServiceEntity")
@Table(name = "services")
@EqualsAndHashCode(callSuper = true)
public class ServiceEntity extends Responsible {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@ManyToMany
	Set<Customer> customers;
}
