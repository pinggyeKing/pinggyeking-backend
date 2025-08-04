package com.swyp10.pinggyewang.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.dto.response.WithImageResponse;
import com.swyp10.pinggyewang.exception.ClovaException;
import com.swyp10.pinggyewang.exception.CustomErrorCode;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
import java.time.Duration;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class ClovaService implements ExcuseGenerator {

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

    objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
  }

  @Override
  public WithImageResponse generateSentence(final ExcuseRequest request) {
    try {
      String requestBody = buildRequestBody(request);

      String apiResponse = callClovaApi(requestBody);

      String extractedContent = extractContentFromResponse(apiResponse);

      WithImageResponse wrapper = parseWithImageResponse(extractedContent);

      excuseRepository.save(wrapper.excuse().toExcuse(request.isRegenerated()));

      return wrapper;
    } catch (Exception e) {
      e.printStackTrace();
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

  private WithImageResponse parseWithImageResponse(String jsonContent) {
       try {
         JsonNode root     = objectMapper.readTree(jsonContent);
         JsonNode actual = root.isTextual() ? objectMapper.readTree(root.textValue()) : root;

         ExcuseResponse excuse = objectMapper.treeToValue(actual, ExcuseResponse.class);

         String imageKey = actual.path("imageKey").asText(null);

         validateResponse(excuse);
         return new WithImageResponse(excuse, imageKey);
       } catch (JsonProcessingException e) {
           throw new ClovaException(CustomErrorCode.CLOVA_JSON_PARSE_EXCEPTION);
       }
  }
}
