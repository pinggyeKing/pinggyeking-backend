package com.swyp10.pinggyewang.domain;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "excuses")
public class Excuse {

  public Excuse() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "situation", nullable = false, length = 500)
  private String situation;

  @Column(name = "target", nullable = false, length = 100)
  private String target;

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

  @Column(name = "ai_created_at")
  private OffsetDateTime aiCreatedAt;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @Builder
  public Excuse(String situation, String target, String tone, String excuse,
      String credibilityWhy, Double credibilityScore, String category,
      String keywords, String alternatives, Integer tokensUsed,
      Long responseTimeMs, OffsetDateTime aiCreatedAt) {
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
    this.aiCreatedAt = aiCreatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getSituation() {
    return situation;
  }

  public String getTarget() {
    return target;
  }

  public String getTone() {
    return tone;
  }

  public String getExcuse() {
    return excuse;
  }

  public String getCredibilityWhy() {
    return credibilityWhy;
  }

  public Double getCredibilityScore() {
    return credibilityScore;
  }

  public String getCategory() {
    return category;
  }

  public String getKeywords() {
    return keywords;
  }

  public String getAlternatives() {
    return alternatives;
  }

  public Integer getTokensUsed() {
    return tokensUsed;
  }

  public Long getResponseTimeMs() {
    return responseTimeMs;
  }

  public OffsetDateTime getAiCreatedAt() {
    return aiCreatedAt;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }
}