package com.swyp10.pinggyewang.dto.request;

import com.swyp10.pinggyewang.domain.Feedback;
import com.swyp10.pinggyewang.domain.Rating;
import com.swyp10.pinggyewang.exception.CustomErrorCode;
import com.swyp10.pinggyewang.exception.ValidateException;

public record FeedbackRequest(Rating rating, String feedback) {
  public FeedbackRequest {
    if (rating == null) {
      throw new ValidateException(CustomErrorCode.RATING_REQUIRED);
    }
    if (feedback == null || feedback.trim().isEmpty()) {
      throw new ValidateException(CustomErrorCode.FEEDBACK_CONTENT_REQUIRED);
    }
    if (feedback.length() > 1000) {
      throw new ValidateException(CustomErrorCode.FEEDBACK_CONTENT_TOO_LONG);
    }
  }

  public Feedback toDomain() {
    return new Feedback(rating, feedback);
  }
}