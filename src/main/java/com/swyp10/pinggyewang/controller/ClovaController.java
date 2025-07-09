package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.service.ClovaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClovaController {

    private final ClovaService clovaService;

    public ClovaController(ClovaService clovaService){
        this.clovaService = clovaService;
    }

    @PostMapping("/api/clova/generate")
    public ResponseEntity<ExcuseResponse> textGenerate(
            @RequestBody ExcuseRequest request) {
        ExcuseResponse response = clovaService.generateSentence(request);
        return ResponseEntity.ok(response);
    }
}
