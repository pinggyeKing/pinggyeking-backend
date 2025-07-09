package com.swyp10.pinggyewang;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.service.ClovaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClovaIntegrationTest {

    @Autowired
    private ClovaService clovaService;

    @Test
    void printApiRequestLog() throws JsonProcessingException {
        ExcuseRequest req = new ExcuseRequest();
        ObjectMapper objectMapper = new ObjectMapper();

        req.setSituation("회의에 늦음");
        req.setTarget("팀장님");
        req.setTone("정중하게");

        ExcuseResponse resp = clovaService.generateSentence(req);

        System.out.println("====== AI 응답 ======");
        System.out.println(resp);

        assert resp.getExcuse() != null;
        assert resp.getCategory() != null;
        assert resp.getCreatedAt() != null;
    }
}
