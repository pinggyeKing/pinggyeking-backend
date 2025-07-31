package com.swyp10.pinggyewang.service;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.request.QuestionRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.service.mock.MockExcuseGenerator;
import com.swyp10.pinggyewang.service.mock.TestClovaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ClovaTest {

    private final MockExcuseGenerator mockExcuseGenerator = new MockExcuseGenerator();

    private final ExcuseGenerator excuseGenerator = new TestClovaService(mockExcuseGenerator);

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void printApiRequestLog() throws JsonProcessingException {
        final String situation = "회의에 늦음";
        final String target = "팀장님";
        final String tone = "정중하게";
        final boolean isRegenerated = false;
        final String regeneratedBtnVal="";
        List<QuestionRequest> questions = List.of(
                new QuestionRequest(1, "구체적으로 어떤 상황이신가요?", "출근길에 육교가 무너졌다."),
                new QuestionRequest(2, "추가로 설명하고 싶은 부분이 있나요? (상황 설명, 정도나 심각성, 관련 배경 등)", "비바람이 많이 불어 육교가 무너진것 같다."),
                new QuestionRequest(3, "상대방에게 전달할 때 고려해야 할 점이 있나요?",  "이모티콘이 조금 섞인 상태로 대답이 생성됐음 좋겠다.")
        );

        ExcuseRequest request = new ExcuseRequest(situation, target, tone, isRegenerated, regeneratedBtnVal, questions);

        ExcuseResponse response = excuseGenerator.generateSentence(request);

        System.out.println("====== AI 응답 ======");
        System.out.println(response);

        assertThat(response.excuse()).isNotNull();
        assertThat(response.category()).isNotNull();
        assertThat(response.createdAt()).isNotNull();
    }
}
