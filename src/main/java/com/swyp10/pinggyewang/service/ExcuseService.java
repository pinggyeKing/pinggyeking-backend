package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.dto.response.ExcuseCountResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.repository.ExcuseRepository;
import java.time.OffsetDateTime;
import java.util.List;
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
}
