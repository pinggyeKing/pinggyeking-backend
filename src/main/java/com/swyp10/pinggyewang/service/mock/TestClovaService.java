package com.swyp10.pinggyewang.service.mock;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.dto.response.WithImageResponse;
import com.swyp10.pinggyewang.service.ExcuseGenerator;
import org.springframework.stereotype.Service;

@Service
public class TestClovaService implements ExcuseGenerator {

  private final MockExcuseGenerator mockExcuseGenerator;

  public TestClovaService(MockExcuseGenerator mockExcuseGenerator) {
    this.mockExcuseGenerator = mockExcuseGenerator;
  }

  @Override
  public WithImageResponse generateSentence(final ExcuseRequest request) {
    return mockExcuseGenerator.generateMockResponse(request);
  }
}
