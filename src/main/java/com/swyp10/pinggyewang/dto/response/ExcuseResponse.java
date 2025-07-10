package com.swyp10.pinggyewang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.domain.Excuse;
import java.time.OffsetDateTime;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ExcuseResponse(
    String situation,
    String target,
    String tone,
    String excuse,
    String credibilityWhy,
    Double credibilityScore,
    String category,
    List<String> keyword,
    List<String> alts,

    @JsonProperty("tokens_used")
    Integer tokensUsed,

    @JsonProperty("response_time_ms")
    Long responseTimeMs,

    @JsonProperty("created_at")
    OffsetDateTime createdAt
) {
    public Excuse toDomain() {
        return Excuse.builder()
            .situation(this.situation)
            .target(this.target)
            .tone(this.tone)
            .excuse(this.excuse)
            .credibilityWhy(this.credibilityWhy)
            .credibilityScore(this.credibilityScore)
            .category(this.category)
            .keywords(listToJson(this.keyword))
            .alternatives(listToJson(this.alts))
            .tokensUsed(this.tokensUsed)
            .responseTimeMs(this.responseTimeMs)
            .aiCreatedAt(this.createdAt)
            .build();
    }

    public static ExcuseResponse of(Excuse excuse) {
        return new ExcuseResponse(
            excuse.getSituation(),
            excuse.getTarget(),
            excuse.getTone(),
            excuse.getExcuse(),
            excuse.getCredibilityWhy(),
            excuse.getCredibilityScore(),
            excuse.getCategory(),
            jsonToList(excuse.getKeywords()),
            jsonToList(excuse.getAlternatives()),
            excuse.getTokensUsed(),
            excuse.getResponseTimeMs(),
            excuse.getAiCreatedAt()
        );
    }

    private String listToJson(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static List<String> jsonToList(String json) {
        if (json == null || json.isEmpty()) {
            return List.of();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }
}
