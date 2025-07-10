package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.domain.Excuse;
import com.swyp10.pinggyewang.dto.response.ExcuseCountResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExcuseServiceTest {

  @Mock
  private ExcuseRepository excuseRepository;

  @InjectMocks
  private ExcuseService excuseService;

  @Test
  void getTotalCount_성공() {
    given(excuseRepository.count()).willReturn(10L);

    ExcuseCountResponse result = excuseService.getTotalCount();

    assertThat(result.count()).isEqualTo(10L);
  }

  @Test
  void getTodayCount_성공() {
    given(excuseRepository.countByCreatedAtBetween(any(OffsetDateTime.class), any(OffsetDateTime.class)))
        .willReturn(5L);

    ExcuseCountResponse result = excuseService.getTodayCount();

    assertThat(result.count()).isEqualTo(5L);
  }

  @Test
  void findAll_성공() {
    Excuse excuse = Excuse.builder()
        .situation("회사")
        .target("상사")
        .tone("정중한")
        .excuse("교통체증으로 늦었습니다")
        .credibilityWhy("실제로 교통체증이 심했음")
        .credibilityScore(8.5)
        .category("지각")
        .keywords("[\"교통\", \"지각\"]")
        .alternatives("[\"차가 고장났습니다\"]")
        .tokensUsed(100)
        .responseTimeMs(2000L)
        .aiCreatedAt(OffsetDateTime.now())
        .build();

    given(excuseRepository.findAll()).willReturn(List.of(excuse));

    List<ExcuseResponse> result = excuseService.findAll();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).situation()).isEqualTo("회사");
    assertThat(result.get(0).excuse()).isEqualTo("교통체증으로 늦었습니다");
  }
}