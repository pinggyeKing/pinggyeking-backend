package com.swyp10.pinggyewang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WithImageResponse(ExcuseResponse excuse, String imageKey, Long id) {

    public static WithImageResponse of(ExcuseResponse excuse, String imageKey) {
        return new WithImageResponse(excuse, imageKey, null);
    }

    public static WithImageResponse of(ExcuseResponse excuse, String imageKey, Long id) {
        return new WithImageResponse(excuse, imageKey, id);
    }
}
