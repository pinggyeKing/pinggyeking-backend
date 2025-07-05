package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.service.ClovaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class ClovaController {

    private final ClovaService clovaService;

    public ClovaController(ClovaService clovaService){
        this.clovaService = clovaService;
    }

    @PostMapping("/api/clova/generate")
    public Mono<ResponseEntity<Map<String,String>>> textGenerate(
            @RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return clovaService.generateSentence(prompt)
                .map(text -> ResponseEntity.ok(Map.of("result", text)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
