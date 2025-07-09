package com.swyp10.pinggyewang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcuseResponse {
    private String situation;

    private String target;

    private String tone;

    private String excuse;

    private String credibilityWhy;

    private Double credibilityScore;

    private String category;

    private List<String> keyword;

    private List<String> alts;

    @JsonProperty("tokens_used")
    private Integer tokensUsed;

    @JsonProperty("response_time_ms")
    private Long responseTimeMs;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;


}
