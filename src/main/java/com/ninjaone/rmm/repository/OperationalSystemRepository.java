package com.ninjaone.rmm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaone.rmm.model.OperationalSystem;


public interface OperationalSystemRepository extends JpaRepository<OperationalSystem, Long>{

	@Query("SELECT o FROM OperationalSystem o WHERE UPPER(o.name) = UPPER(:name)")
	Optional<OperationalSystem> findByName(String name);
	
}
