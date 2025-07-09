package com.swyp10.pinggyewang.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ClovaService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String systemPrompt;

    public ClovaService(WebClient webClient, ObjectMapper objectMapper, @Value("${clova.system-prompt}") String systemPrompt){
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.systemPrompt = systemPrompt;
    }

    public ExcuseResponse generateSentence(ExcuseRequest req) {

        try {
            String userJson = objectMapper.writeValueAsString(req);

            List<Map<String,String>> messages = List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userJson)
            );

            Map<String, Object> body = Map.of(
                    "model", "HCX-005",
                    "messages", messages,
                    "maxTokens", 512,
                    "temperature", 0.7
            );

            String response = webClient.post()
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (response == null || response.isBlank()) {
                throw new RuntimeException("클로바 API가 빈 응답을 반환했습니다.");
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode contentNode = root.at("/result/message/content");
            if (contentNode.isMissingNode()) {
                throw new RuntimeException("content 필드를 찾을 수 없습니다: " + root);
            }
            String content = contentNode.asText()
                    .replaceFirst("(?m)^```json\\s*\\n?", "")
                    .replaceFirst("(?m)\\n?```$", "")
                    .trim();

            System.out.println("===== Final JSON to parse =====");
            System.out.println(content);


            return objectMapper.readValue(content, ExcuseResponse.class);

        } catch (Exception e){
            throw  new RuntimeException("AI 호출 또는 파싱 실패", e);
        }
    }


}
