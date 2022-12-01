package com.ninjaone.rmm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class Swagger3Cinfig {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("NinjaOne RMM API")
						.description("Remote Monitoring and Management (RMM) backend platform helps IT professionals manage fleet of Devices with Services associated with them")
				.version("1.0")
				.license(new License()
							.name("Apache 2.0")
							.url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}
	
	// If JWT security needs
	/*
	@Bean
	 public OpenAPI customOpenAPI() {
	   return new OpenAPI()
	          .components(new Components()
	          .addSecuritySchemes("bearer-key",
	          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
	 */
	
}
