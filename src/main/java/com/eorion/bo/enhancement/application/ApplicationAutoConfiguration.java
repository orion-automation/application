package com.eorion.bo.enhancement.application;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan
public class ApplicationAutoConfiguration {
    @Bean
    public GroupedOpenApi applicationGroup() {
        return GroupedOpenApi.builder()
                .group("application")
                .packagesToScan("com.eorion.bo.enhancement.application.adapter.inbound")
                .pathsToMatch("/enhancement/application/**", "/enhancement/pda-application/**")
                .build();
    }
}
