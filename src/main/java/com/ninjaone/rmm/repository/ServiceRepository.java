package com.ninjaone.rmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaone.rmm.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>{
	
	@Query("SELECT s FROM ServiceEntity s WHERE UPPER(s.description) = UPPER(:description)")
	ServiceEntity findByDescription(String description);

}
