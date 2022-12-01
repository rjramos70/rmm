package com.ninjaone.rmm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaone.rmm.model.OperationalSystem;
import com.ninjaone.rmm.model.Type;


public interface TypeRepository extends JpaRepository<Type, Long>{

	@Query("SELECT t FROM Type t WHERE UPPER(t.description) = UPPER(:description)")
	Type findByDescription(String description);
	
	@Query("SELECT t FROM Type t WHERE t.operationalSystem = :operationalSystem")
	List<Type> findTypeByOperationalSystem(OperationalSystem operationalSystem);
	
}
