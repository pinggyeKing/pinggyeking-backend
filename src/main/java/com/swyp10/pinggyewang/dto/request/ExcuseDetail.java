package com.swyp10.pinggyewang.dto.request;

import com.swyp10.pinggyewang.domain.Target;

public record ExcuseDetail(String excuse, String situation, Target target, String tone) {
}
