package com.ecoomrce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@RefreshScope
@Data
public class FeaturesEnableConfig {

	@Value("${feature.user-tracking-enabled:false}")
	private boolean isUserTrackingEnabled;
}
