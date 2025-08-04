package com.swyp10.pinggyewang.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "excuses")
@Getter
public class Excuse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "situation", nullable = false, length = 500)
  private String situation;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Target target;

  @Column(name = "tone", nullable = false, length = 50)
  private String tone;

  @Column(name = "excuse", nullable = false, columnDefinition = "TEXT")
  private String excuse;

  @Column(name = "credibility_why", columnDefinition = "TEXT")
  private String credibilityWhy;

  @Column(name = "credibility_score")
  private Double credibilityScore;

  @Column(name = "category", length = 100)
  private String category;

  @Column(name = "keyword", columnDefinition = "TEXT")
  private String keywords;

  @Column(name = "alts", columnDefinition = "TEXT")
  private String alternatives;

  @Column(name = "tokens_used")
  private Integer tokensUsed;

  @Column(name = "response_time_ms")
  private Long responseTimeMs;

  @Column(name = "is_regenerated", columnDefinition = "TINYINT(1)")
  private Boolean isRegenerated;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Excuse() {
  }

  @Builder
  public Excuse(String situation, Target target, String tone, String excuse,
      String credibilityWhy, Double credibilityScore, String category,
      String keywords, String alternatives, Integer tokensUsed,
      Long responseTimeMs, Boolean isRegenerated) {
    this.situation = situation;
    this.target = target;
    this.tone = tone;
    this.excuse = excuse;
    this.credibilityWhy = credibilityWhy;
    this.credibilityScore = credibilityScore;
    this.category = category;
    this.keywords = keywords;
    this.alternatives = alternatives;
    this.tokensUsed = tokensUsed;
    this.responseTimeMs = responseTimeMs;
    this.isRegenerated = isRegenerated;
  }
}
