package com.swyp10.pinggyewang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WithImageResponse(ExcuseResponse excuse, String imageKey) {
}
