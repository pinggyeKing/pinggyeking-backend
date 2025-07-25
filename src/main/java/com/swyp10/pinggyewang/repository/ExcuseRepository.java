package com.swyp10.pinggyewang.repository;

import com.swyp10.pinggyewang.domain.Excuse;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Long> {

  Long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  @Query("SELECT " +
      "COUNT(e) as totalCount, " +
      "AVG(COALESCE(e.credibilityScore, 0)) as avgSatisfaction, " +
      "SUM(CASE WHEN e.isRegenerated = true THEN 1 ELSE 0 END) as regeneratedCount " +
      "FROM Excuse e")
  List<Object[]> getBasicStatistics(); // Object[] -> List<Object[]>로 변경

  @Query("SELECT HOUR(e.createdAt) as hour, COUNT(e) as count " +
      "FROM Excuse e " +
      "GROUP BY HOUR(e.createdAt) " +
      "ORDER BY count DESC " +
      "LIMIT 1")
  List<Object[]> getPeakTimeStatistics(); // Object[] -> List<Object[]>로 변경
}