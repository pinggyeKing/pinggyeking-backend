package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.dto.response.ExcuseCountResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseStatisticsResponse;
import com.swyp10.pinggyewang.dto.response.PeakTimeResponse;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ExcuseService {

  private final ExcuseRepository excuseRepository;

  public ExcuseService(final ExcuseRepository excuseRepository) {
    this.excuseRepository = excuseRepository;
  }

  public ExcuseCountResponse getTotalCount() {
    return new ExcuseCountResponse(excuseRepository.count());
  }

  public ExcuseCountResponse getTodayCount() {
    LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        .withNano(0);
    LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59)
        .withNano(999999999);

    return new ExcuseCountResponse(excuseRepository.countByCreatedAtBetween(startOfDay, endOfDay));
  }

  public List<ExcuseResponse> findAll() {
    return excuseRepository.findAll().stream()
        .map(ExcuseResponse::of)
        .toList();
  }

  public ExcuseStatisticsResponse getStatistics() {
    List<Object[]> basicStatsList = excuseRepository.getBasicStatistics();

    Long totalCount = 0L;
    Double averageSatisfaction = 0.0;
    Long regeneratedCount = 0L;

    if (!basicStatsList.isEmpty()) {
      Object[] basicStats = basicStatsList.get(0); // 첫 번째 행 가져오기
      totalCount = ((Number) basicStats[0]).longValue();
      averageSatisfaction = basicStats[1] != null ? ((Number) basicStats[1]).doubleValue() : 0.0;
      regeneratedCount = ((Number) basicStats[2]).longValue();
    }

    double regenerationRate = totalCount > 0 ?
        (regeneratedCount.doubleValue() / totalCount.doubleValue()) * 100 : 0.0;

    List<Object[]> peakTimeList = excuseRepository.getPeakTimeStatistics();
    PeakTimeResponse peakTime = null;

    if (!peakTimeList.isEmpty()) {
      Object[] peakTimeData = peakTimeList.get(0);
      peakTime = new PeakTimeResponse(
          ((Number) peakTimeData[0]).intValue(),
          ((Number) peakTimeData[1]).longValue()
      );
    }

    return new ExcuseStatisticsResponse(
        totalCount,
        Math.round(averageSatisfaction * 100.0) / 100.0,
        Math.round(regenerationRate * 100.0) / 100.0,
        peakTime
    );
  }
}
