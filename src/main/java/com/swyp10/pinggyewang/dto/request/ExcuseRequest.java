package com.swyp10.pinggyewang.dto.request;

public record ExcuseRequest(
    String situation, String target, String tone, boolean isRegenerated
) {

}
