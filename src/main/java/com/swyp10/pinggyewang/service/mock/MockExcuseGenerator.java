package com.swyp10.pinggyewang.service.mock;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MockExcuseGenerator {

  public ExcuseResponse generateMockResponse(ExcuseRequest request) {
    return new ExcuseResponse(
        "회의에 늦음",  // situation
        request.target(),  // target (요청에서 받은 값 사용)
        request.tone(),    // tone (요청에서 받은 값 사용)
        "죄송합니다, " + request.target() + ". 오늘 아침 교통 상황이 예상보다 훨씬 나빠서 회의에 늦게 도착했습니다.",  // excuse
        "교통 상황은 예측하기 어려운 경우가 많으며, 출근길 교통 체증은 많은 사람들이 겪는 문제이므로 믿을 만한 이유입니다.",  // credibilityWhy
        8.0,  // credibilityScore
        "시간 관리 실패",  // category
        List.of("교통 체증", "예측 불가능"),  // keyword
        List.of(
            "오늘 도로 공사 때문에 지연되었습니다.",
            "예상치 못한 차량 고장으로 인해 지각했습니다."
        ),  // alts
        10,  // tokensUsed
        300L,  // responseTimeMs
        OffsetDateTime.now()  // createdAt
    );
  }
}
