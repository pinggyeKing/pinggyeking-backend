package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.domain.Feedback;
import com.swyp10.pinggyewang.dto.request.FeedbackRequest;
import com.swyp10.pinggyewang.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

  private final FeedbackRepository feedbackRepository;

  public FeedbackService(final FeedbackRepository feedbackRepository) {
    this.feedbackRepository = feedbackRepository;
  }

  public void createFeedback(final FeedbackRequest feedbackRequest) {
    final Feedback feedback = feedbackRequest.toDomain();

    feedbackRepository.save(feedback);
  }
}
