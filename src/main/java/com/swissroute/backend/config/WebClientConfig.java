package com.swissroute.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${swiss-transport.api.base-url}")
    private String baseUrl;

    @Bean
    public WebClient swissTransportWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
