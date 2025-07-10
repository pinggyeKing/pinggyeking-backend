package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;

public interface ExcuseGenerator {
  ExcuseResponse generateSentence(ExcuseRequest request);
}
