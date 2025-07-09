package com.swyp10.pinggyewang.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Feedback 도메인 테스트")
class FeedbackTest {

  @Test
  @DisplayName("기본 생성자로 Feedback 객체를 생성할 수 있다")
  void createFeedback_WithDefaultConstructor() {
    Feedback feedback = new Feedback();

    assertThat(feedback).isNotNull();
    assertThat(feedback.getId()).isNull();
    assertThat(feedback.getRating()).isNull();
    assertThat(feedback.getFeedback()).isNull();
    assertThat(feedback.getCreatedAt()).isNull();
  }

  @Test
  @DisplayName("매개변수 생성자로 Feedback 객체를 생성할 수 있다")
  void createFeedback_WithParameterConstructor() {
    Rating rating = Rating.LIKE;
    String feedbackContent = "서비스가 정말 좋네요!";

    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback).isNotNull();
    assertThat(feedback.getId()).isNull();
    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
    assertThat(feedback.getCreatedAt()).isNull();
  }

  @ParameterizedTest
  @EnumSource(Rating.class)
  @DisplayName("모든 Rating enum 값으로 Feedback을 생성할 수 있다")
  void createFeedback_WithAllRatingValues(Rating rating) {
    String feedbackContent = "테스트 피드백";

    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "짧은 피드백",
      "이것은 조금 더 긴 피드백 내용입니다. 사용자가 서비스에 대해 상세한 의견을 남길 수 있습니다.",
      "",
      " ",
      "특수문자가 포함된 피드백! @#$%^&*()_+-=[]{}|;':\",./<>?"
  })
  @DisplayName("다양한 길이와 형태의 피드백 내용으로 Feedback을 생성할 수 있다")
  void createFeedback_WithVariousFeedbackContents(String feedbackContent) {
    Rating rating = Rating.LIKE;

    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
  }

  @Test
  @DisplayName("null 값으로 Feedback을 생성할 수 있다")
  void createFeedback_WithNullValues() {
    Feedback feedback = new Feedback(null, null);

    assertThat(feedback).isNotNull();
    assertThat(feedback.getRating()).isNull();
    assertThat(feedback.getFeedback()).isNull();
  }

  @Test
  @DisplayName("LIKE 평점으로 긍정적인 피드백을 생성할 수 있다")
  void createFeedback_PositiveFeedback() {
    Rating rating = Rating.LIKE;
    String feedbackContent = "정말 만족스러운 서비스입니다. 계속 이용하고 싶어요!";

    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback.getRating()).isEqualTo(Rating.LIKE);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
  }

  @Test
  @DisplayName("DISLIKE 평점으로 부정적인 피드백을 생성할 수 있다")
  void createFeedback_NegativeFeedback() {
    Rating rating = Rating.DISLIKE;
    String feedbackContent = "서비스에 개선이 필요할 것 같습니다.";

    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback.getRating()).isEqualTo(Rating.DISLIKE);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
  }

  @Test
  @DisplayName("매우 긴 피드백 내용으로 Feedback을 생성할 수 있다")
  void createFeedback_WithLongContent() {
    Rating rating = Rating.LIKE;
    String longFeedback = "a".repeat(1000); // 1000자

    Feedback feedback = new Feedback(rating, longFeedback);

    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(longFeedback);
    assertThat(feedback.getFeedback()).hasSize(1000);
  }

  @Test
  @DisplayName("Feedback 객체의 모든 getter 메서드가 올바르게 동작한다")
  void testAllGetters() {
    Rating rating = Rating.LIKE;
    String feedbackContent = "테스트 피드백";
    Feedback feedback = new Feedback(rating, feedbackContent);

    assertThat(feedback.getId()).isNull(); // 영속화되지 않은 상태
    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(feedbackContent);
    assertThat(feedback.getCreatedAt()).isNull(); // @CreationTimestamp는 영속화 시점에 설정
  }

  @Test
  @DisplayName("동일한 값으로 생성된 두 Feedback 객체는 내용이 같다")
  void createFeedback_SameContent() {
    Rating rating = Rating.LIKE;
    String feedbackContent = "동일한 피드백";

    Feedback feedback1 = new Feedback(rating, feedbackContent);
    Feedback feedback2 = new Feedback(rating, feedbackContent);

    assertThat(feedback1.getRating()).isEqualTo(feedback2.getRating());
    assertThat(feedback1.getFeedback()).isEqualTo(feedback2.getFeedback());
    assertThat(feedback1).isNotEqualTo(feedback2);
  }

  @Test
  @DisplayName("빈 문자열 피드백으로 Feedback을 생성할 수 있다")
  void createFeedback_WithEmptyString() {
    Rating rating = Rating.DISLIKE;
    String emptyFeedback = "";

    Feedback feedback = new Feedback(rating, emptyFeedback);

    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEmpty();
  }

  @Test
  @DisplayName("공백만 있는 피드백으로 Feedback을 생성할 수 있다")
  void createFeedback_WithWhitespaceOnly() {
    Rating rating = Rating.LIKE;
    String whitespaceFeedback = "   \t\n   ";

    Feedback feedback = new Feedback(rating, whitespaceFeedback);

    assertThat(feedback.getRating()).isEqualTo(rating);
    assertThat(feedback.getFeedback()).isEqualTo(whitespaceFeedback);
  }
}