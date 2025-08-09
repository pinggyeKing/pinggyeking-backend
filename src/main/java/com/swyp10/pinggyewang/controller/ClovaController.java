package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.domain.Target;
import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.WithImageResponse;
import com.swyp10.pinggyewang.service.ExcuseGenerator;
import com.swyp10.pinggyewang.service.ImageMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClovaController {

  private final ExcuseGenerator excuseGenerator;

  private final ImageMappingService imageMappingService;

  public ClovaController(ExcuseGenerator excuseGenerator, ImageMappingService imageMappingService) {
    this.excuseGenerator = excuseGenerator;
      this.imageMappingService = imageMappingService;
  }

  @PostMapping("/api/clova/generate")
  public ResponseEntity<WithImageResponse> textGenerate(@RequestBody ExcuseRequest request) {

    Target enumTarget = Target.of(request.target());

    WithImageResponse excuse = excuseGenerator.generateSentence(request);

    String imageKey = imageMappingService.selectImageKey(enumTarget, request.tone());

    WithImageResponse response = new WithImageResponse(excuse.excuse(), imageKey, excuse.id());

    return ResponseEntity.ok(response);
  }
}
