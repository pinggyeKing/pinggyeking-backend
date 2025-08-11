package com.swyp10.pinggyewang.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.swyp10.pinggyewang.domain.Feedback;
import com.swyp10.pinggyewang.domain.Rating;
import com.swyp10.pinggyewang.dto.request.FeedbackRequest;
import com.swyp10.pinggyewang.exception.ApplicationException;
import com.swyp10.pinggyewang.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FeedbackServiceTest {

  @Mock
  private FeedbackRepository feedbackRepository;

  @InjectMocks
  private FeedbackService feedbackService;

  @Test
  @DisplayName("LIKE 피드백을 성공적으로 생성한다")
  void createFeedback_Like_Success() {
    FeedbackRequest request = new FeedbackRequest(Rating.LIKE, "좋은 서비스입니다!");
    Feedback savedFeedback = new Feedback(Rating.LIKE, "좋은 서비스입니다!");
    given(feedbackRepository.save(any(Feedback.class))).willReturn(savedFeedback);

    feedbackService.createFeedback(request);

    verify(feedbackRepository).save(any(Feedback.class));
  }

  @Test
  @DisplayName("DISLIKE 피드백을 성공적으로 생성한다")
  void createFeedback_Dislike_Success() {
    FeedbackRequest request = new FeedbackRequest(Rating.DISLIKE, "개선이 필요해요");

    feedbackService.createFeedback(request);

    verify(feedbackRepository).save(any(Feedback.class));
  }

  @Test
  @DisplayName("피드백 내용도 성공적으로 생성한다")
  void createFeedback_content_Success() {
    String longContent = "정말 ".repeat(5) + "좋은 서비스입니다!";
    FeedbackRequest request = new FeedbackRequest(Rating.LIKE, longContent);

    feedbackService.createFeedback(request);

    verify(feedbackRepository).save(any(Feedback.class));
  }
}