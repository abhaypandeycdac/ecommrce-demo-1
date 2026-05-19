package com.ecoomrce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class AppConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
     RestClient restClient() {
        return RestClient.builder().build();
    }
	
	@Bean
    Capability capability(final MeterRegistry registry) {
       return new MicrometerCapability(registry);
   }
}
