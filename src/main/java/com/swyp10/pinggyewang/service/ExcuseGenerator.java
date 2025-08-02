package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.dto.request.ExcuseRequest;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.dto.response.WithImageResponse;

public interface ExcuseGenerator {
  WithImageResponse generateSentence(ExcuseRequest request);
}
