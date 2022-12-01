package com.ninjaone.rmm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaone.rmm.model.OperationalSystem;
import com.ninjaone.rmm.model.PricePerService;
import com.ninjaone.rmm.model.ServiceEntity;

public interface PricePerServiceRepository extends JpaRepository<PricePerService, Long>{
	
	@Query("SELECT p FROM PricePerService p WHERE p.serviceEntity = :serviceEntity AND p.operationalSystem = :operationalSystem")
	PricePerService findPricePerServiceByServiceIdAndOperationalSystemId(ServiceEntity serviceEntity, OperationalSystem operationalSystem);
	
	@Query("SELECT p FROM PricePerService p WHERE p.operationalSystem = :operationalSystem")
	List<PricePerService> findPricePerServiceByServiceIdByOperationalSystem(OperationalSystem operationalSystem);

}
