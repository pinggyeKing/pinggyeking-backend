package com.swyp10.pinggyewang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

}
