package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.dto.response.ExcuseCountResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
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
    OffsetDateTime startOfDay = OffsetDateTime.now().withHour(0).withMinute(0).withSecond(0)
        .withNano(0);
    OffsetDateTime endOfDay = OffsetDateTime.now().withHour(23).withMinute(59).withSecond(59)
        .withNano(999999999);

    return new ExcuseCountResponse(excuseRepository.countByCreatedAtBetween(startOfDay, endOfDay));
  }

  public List<ExcuseResponse> findAll() {
    return excuseRepository.findAll().stream()
        .map(ExcuseResponse::of)
        .toList();
  }

  public Map<String, Object> getDashboardStats() {
    List<Object[]> results = excuseRepository.findDashboardStats();

    Object[] row = results.get(0);

    Map<String, Object> stats = new HashMap<>();
    stats.put("totalExcuses", ((Number) row[0]).longValue());
    stats.put("todayExcuses", ((Number) row[1]).longValue());
    stats.put("avgCredibilityScore", ((Number) row[2]).doubleValue());
    stats.put("avgResponseTime", ((Number) row[3]).longValue());
    stats.put("totalTokensUsed", ((Number) row[4]).longValue());
    stats.put("activeUsers", ((Number) row[5]).longValue());

    return stats;
  }

  private Long safeConvertToLong(Object value) {
    if (value == null) return 0L;
    return ((Number) value).longValue();
  }

  private Double safeConvertToDouble(Object value) {
    if (value == null) return 0.0;
    return ((Number) value).doubleValue();
  }
}
