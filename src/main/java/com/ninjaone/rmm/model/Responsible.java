package com.ninjaone.rmm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@MappedSuperclass
public class Responsible {
	
	@NotBlank(message = "Login is mandatory")
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_on")
	private Date updatedOn;

}
