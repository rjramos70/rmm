package com.ninjaone.rmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaone.rmm.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>{
	
	@Query("SELECT d FROM Device d WHERE UPPER(d.systemName) = UPPER(:systemName)")
	Device findBySystemName(String systemName);

}
