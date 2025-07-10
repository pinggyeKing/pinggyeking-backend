package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.service.ExcuseGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClovaController {

    private final ExcuseGenerator excuseGenerator;

    public ClovaController(ExcuseGenerator excuseGenerator){
        this.excuseGenerator = excuseGenerator;
    }

    @PostMapping("/api/clova/generate")
    public ResponseEntity<ExcuseResponse> textGenerate(
            @RequestBody ExcuseRequest request) {
        ExcuseResponse response = excuseGenerator.generateSentence(request);
        return ResponseEntity.ok(response);
    }
}
