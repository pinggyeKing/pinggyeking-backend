package com.swyp10.pinggyewang.dto.response;

import com.swyp10.pinggyewang.domain.Target;

public record TargetSatisfactionResponse(
    Target target,
    String targetName,
    Double avg,
    Long totalCount
) {

}
