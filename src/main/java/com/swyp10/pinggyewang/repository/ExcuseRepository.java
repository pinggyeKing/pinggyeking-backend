package com.swyp10.pinggyewang.repository;

import com.swyp10.pinggyewang.domain.Excuse;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
  Long countByCreatedAtBetween(OffsetDateTime startDate, OffsetDateTime endDate);
}
