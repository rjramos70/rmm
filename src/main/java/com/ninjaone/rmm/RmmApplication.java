package com.ninjaone.rmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RmmApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmmApplication.class, args);
	}

}
