package com.swyp10.pinggyewang.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.exception.ClovaException;
import com.swyp10.pinggyewang.exception.CustomErrorCode;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
import java.time.Duration;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ClovaService {

  private static final String MODEL_NAME = "HCX-005";
  private static final int MAX_TOKENS = 512;
  private static final double TEMPERATURE = 0.7;
  private static final Duration TIMEOUT = Duration.ofSeconds(30);

  private static final Pattern JSON_CODE_BLOCK_PATTERN =
      Pattern.compile("(?m)^```json\\s*\\n?|\\n?```$");

  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final String systemPrompt;

  private final ExcuseRepository excuseRepository;

  public ClovaService(WebClient webClient, ObjectMapper objectMapper,
      @Value("${clova.system-prompt}") String systemPrompt,
      final ExcuseRepository excuseRepository) {
    this.webClient = webClient;
    this.objectMapper = objectMapper;
    this.systemPrompt = systemPrompt;
    this.excuseRepository = excuseRepository;
  }

  public ExcuseResponse generateSentence(ExcuseRequest request) {
    try {
      String requestBody = buildRequestBody(request);
      String apiResponse = callClovaApi(requestBody);
      String extractedContent = extractContentFromResponse(apiResponse);
      ExcuseResponse response = parseExcuseResponse(extractedContent);

      excuseRepository.save(response.toDomain());

      return response;
    } catch (Exception e) {
      throw new ClovaException(CustomErrorCode.CLOVA_EXCEPTION);
    }
  }

  private String buildRequestBody(ExcuseRequest request) throws JsonProcessingException {
    String userContent = objectMapper.writeValueAsString(request);

    List<Map<String, String>> messages = List.of(
        Map.of("role", "system", "content", systemPrompt),
        Map.of("role", "user", "content", userContent)
    );

    Map<String, Object> requestBody = Map.of(
        "model", MODEL_NAME,
        "messages", messages,
        "maxTokens", MAX_TOKENS,
        "temperature", TEMPERATURE
    );

    return objectMapper.writeValueAsString(requestBody);
  }

  private String callClovaApi(String requestBody) {
    try {
      String response = webClient.post()
          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(requestBody)
          .retrieve()
          .bodyToMono(String.class)
          .timeout(TIMEOUT)
          .block();

      if (!StringUtils.hasText(response)) {
        throw new ClovaException(CustomErrorCode.CLOVA_RESPONSE_EMPTY);
      }

      return response;

    } catch (Exception e) {
      throw new ClovaException(CustomErrorCode.CLOVA_EXCEPTION);
    }
  }

  private String extractContentFromResponse(String apiResponse) throws JsonProcessingException {
    JsonNode root = objectMapper.readTree(apiResponse);
    JsonNode contentNode = root.at("/result/message/content");

    if (contentNode.isMissingNode() || !contentNode.isTextual()) {
      throw new ClovaException(CustomErrorCode.CLOVA_CONTENT_NOT_FOUND);
    }

    String rawContent = contentNode.asText();

    return cleanJsonContent(rawContent);
  }

  private String cleanJsonContent(String content) {
    return JSON_CODE_BLOCK_PATTERN.matcher(content).replaceAll("").trim();
  }

  private void validateResponse(ExcuseResponse response) {
    if (response == null) {
      throw new ClovaException(CustomErrorCode.CLOVA_RESPONSE_EMPTY);
    }

    if (!StringUtils.hasText(response.excuse())) {
      throw new ClovaException(CustomErrorCode.CLOVA_EXCUSE_EMPTY);
    }
  }

  private ExcuseResponse parseExcuseResponse(String jsonContent) throws JsonProcessingException {
    try {
      ExcuseResponse response = objectMapper.readValue(jsonContent, ExcuseResponse.class);
      validateResponse(response);
      return response;

    } catch (JsonProcessingException e) {
      throw new ClovaException(CustomErrorCode.CLOVA_JSON_PARSE_EXCEPTION);
    }
  }
}
