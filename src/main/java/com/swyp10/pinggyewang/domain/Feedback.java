package com.swyp10.pinggyewang.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "feedback")
public class Feedback {

  public Feedback() {
  }

  public Feedback(final Rating rating, final String feedback) {
    this.rating = rating;
    this.feedback = feedback;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rating rating;

  @Column(columnDefinition = "TEXT")
  private String feedback;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public Long getId() {
    return id;
  }

  public Rating getRating() {
    return rating;
  }

  public String getFeedback() {
    return feedback;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
