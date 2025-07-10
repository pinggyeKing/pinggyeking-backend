package com.swyp10.pinggyewang.service;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ClovaIntegrationTest {

    @Autowired
    private ExcuseGenerator excuseGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void printApiRequestLog() throws JsonProcessingException {
        final String situation = "회의에 늦음";
        final String target = "팀장님";
        final String tone = "정중하게";

        ExcuseRequest request = new ExcuseRequest(situation, target, tone);

        ExcuseResponse response = excuseGenerator.generateSentence(request);

        System.out.println("====== AI 응답 ======");
        System.out.println(response);

        assertThat(response.excuse()).isNotNull();
        assertThat(response.category()).isNotNull();
        assertThat(response.createdAt()).isNotNull();
    }
}
