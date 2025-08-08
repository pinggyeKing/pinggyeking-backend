package com.swyp10.pinggyewang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Configuration
public class ClovaStudioConfig {

    @Value("${clova.api.url}")
    private String apiUrl;

    @Value("${clova.api.key}")
    private String apiKey;

    @Bean
    public WebClient clovaWebClient() {

        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }
}
