package com.swyp10.pinggyewang.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ClovaService {
    private final WebClient webClient;

    public ClovaService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<String> generateSentence(String userPrompt) {

        Map<String, Object> body = Map.of(
                "model", "HCX-005",
                "messages", List.of(
                        Map.of("role", "user", "content", userPrompt)
                ),
                "maxTokens", 512,
                "temperature", 0.7
        );

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json ->
                        json.at("/choices/0/message/content").asText()
                );
    }


}
