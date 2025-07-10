package com.swyp10.pinggyewang.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.FeedbackRequest;
import com.swyp10.pinggyewang.service.FeedbackService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("FeedbackController 테스트")
class FeedbackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private FeedbackService feedbackService;

  @Test
  @DisplayName("LIKE 피드백 생성 성공")
  @WithMockUser
  void createFeedback_Like_Success() throws Exception {
    String requestBody = """
            {
                "rating": "LIKE",
                "feedback": "정말 좋은 서비스입니다!"
            }
            """;

    mockMvc.perform(post("/api/feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated());

    verify(feedbackService).createFeedback(any(FeedbackRequest.class));
  }

  @Test
  @DisplayName("DISLIKE 피드백 생성 성공")
  @WithMockUser
  void createFeedback_Dislike_Success() throws Exception {
    String requestBody = """
            {
                "rating": "DISLIKE",
                "feedback": "개선이 필요한 것 같아요"
            }
            """;

    mockMvc.perform(post("/api/feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated());

    verify(feedbackService).createFeedback(any(FeedbackRequest.class));
  }

  @Test
  @DisplayName("잘못된 rating 값으로 요청 시 400 에러")
  @WithMockUser
  void createFeedback_InvalidRating_BadRequest() throws Exception {
    String requestBody = """
            {
                "rating": "INVALID",
                "feedback": "피드백 내용"
            }
            """;

    mockMvc.perform(post("/api/feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest());
  }
}