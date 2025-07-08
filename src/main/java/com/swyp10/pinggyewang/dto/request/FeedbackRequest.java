package com.swyp10.pinggyewang.dto.request;

import com.swyp10.pinggyewang.domain.Feedback;
import com.swyp10.pinggyewang.domain.Rating;

public record FeedbackRequest(Rating rating, String feedback) {
  public Feedback toDomain() {
    return new Feedback(rating, feedback);
  }
}