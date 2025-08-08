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
  List<Object[]> getBasicStatistics();

  @Query("SELECT HOUR(e.createdAt) as hour, COUNT(e) as count " +
      "FROM Excuse e " +
      "GROUP BY HOUR(e.createdAt) " +
      "ORDER BY count DESC " +
      "LIMIT 1")
  List<Object[]> getPeakTimeStatistics();

  // Target별 만족도 통계 조회
  @Query("""
        SELECT 
            e.target,
            AVG(
                CASE 
                    WHEN e.userLiked IS NULL AND e.hasImage = true THEN 60.0
                    WHEN e.userLiked IS NULL AND (e.hasImage IS NULL OR e.hasImage = false) THEN 40.0
                    WHEN e.userLiked = true AND e.hasImage = true THEN 100.0
                    WHEN e.userLiked = true AND (e.hasImage IS NULL OR e.hasImage = false) THEN 80.0
                    WHEN e.userLiked = false AND e.hasImage = true THEN 20.0
                    WHEN e.userLiked = false AND (e.hasImage IS NULL OR e.hasImage = false) THEN 0.0
                    ELSE 40.0
                END
            ) as avg,
            COUNT(e.id) as totalCount
        FROM Excuse e 
        GROUP BY e.target
        ORDER BY avg DESC
        """)
  List<Object[]> getSatisfactionByTarget();

  @Query(value = """
        SELECT 
            DAYOFWEEK(created_at) as dayOfWeek,
            COUNT(id) as totalCount
        FROM excuses 
        GROUP BY DAYOFWEEK(created_at)
        ORDER BY DAYOFWEEK(created_at)
        """, nativeQuery = true)
  List<Object[]> getExcuseCountByDayOfWeek();
}