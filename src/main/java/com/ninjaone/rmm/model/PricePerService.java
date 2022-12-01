package com.ninjaone.rmm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "price_per_service")
@EqualsAndHashCode(callSuper = true)
public class PricePerService extends Responsible {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double price;
	
	@OneToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
	private ServiceEntity serviceEntity;
	
	@OneToOne
    @JoinColumn(name = "op_id", referencedColumnName = "id")
	private OperationalSystem operationalSystem;
	
}
