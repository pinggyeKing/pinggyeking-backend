package com.swyp10.pinggyewang.repository;

import com.swyp10.pinggyewang.domain.Excuse;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Long> {

  Long countByCreatedAtBetween(OffsetDateTime startDate, OffsetDateTime endDate);

  @Query(value = """
    SELECT 
        COUNT(*) as total_excuses,
        COUNT(CASE WHEN DATE(created_at) = CURDATE() THEN 1 END) as today_excuses,
        COALESCE(ROUND(AVG(credibility_score), 1), 0) as avg_credibility_score,
        COALESCE(ROUND(AVG(response_time_ms)), 0) as avg_response_time,
        COALESCE(SUM(tokens_used), 0) as total_tokens_used,
        COUNT(DISTINCT CASE WHEN created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY) THEN situation END) as active_users
    FROM excuses
    """, nativeQuery = true)
  List<Object[]> findDashboardStats();
}
