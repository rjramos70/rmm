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
@Entity(name = "Customer")
@Table(name = "customers")
@EqualsAndHashCode(callSuper = true)
public class Customer extends Responsible {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToMany
	private Set<Device> devices;
	
	@ManyToMany
	private Set<ServiceEntity> services;
	
	private Double totalCost;
	
	public void detachDeviceBySet(Set<Device> deviceSet) {
		devices.removeAll(deviceSet);
	}

	public void detachServiceBySet(Set<ServiceEntity> serviceSet) {
		services.removeAll(serviceSet);
	}
}
