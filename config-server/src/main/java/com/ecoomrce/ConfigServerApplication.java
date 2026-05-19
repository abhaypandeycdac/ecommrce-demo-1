package com.ecoomrce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
//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git remote add origin https://abhaypandey2342-ctrl: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@github.com/abhaypandey2342-ctrl/ecommrce-config-server.git
