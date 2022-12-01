package com.ninjaone.rmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaone.rmm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	

}
