package com.ninjaone.rmm.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity(name = "Device")
@Table(name = "device")
@EqualsAndHashCode(callSuper = true)
public class Device extends Responsible {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String systemName;
	
	@Column(columnDefinition = "integer default 1")
	private Integer qtd;
	
	@NonNull
	@OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
	private Type type;
	
	@ManyToMany
	Set<Customer> customers;
	
}
