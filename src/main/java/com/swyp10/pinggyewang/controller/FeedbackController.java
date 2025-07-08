package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.request.FeedbackRequest;
import com.swyp10.pinggyewang.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(final FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping("/api/feedback")
  @ResponseStatus(HttpStatus.CREATED)
  public void createFeedback(@RequestBody FeedbackRequest request) {
    feedbackService.createFeedback(request);
  }
}
