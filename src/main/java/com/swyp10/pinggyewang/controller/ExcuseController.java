package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.response.ExcuseCountResponse;
import com.swyp10.pinggyewang.dto.response.ExcuseResponse;
import com.swyp10.pinggyewang.service.ExcuseService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excuses")
public class ExcuseController {

  private final ExcuseService excuseService;

  public ExcuseController(final ExcuseService excuseService) {
    this.excuseService = excuseService;
  }

  @GetMapping
  public List<ExcuseResponse> getExcuses() {
    return excuseService.findAll();
  }

  @GetMapping("/count/total")
  public ExcuseCountResponse getTotalExcuseCount() {
    return excuseService.getTotalCount();
  }

  @GetMapping("/count/today")
  public ExcuseCountResponse getTodayExcuseCount() {
    return excuseService.getTodayCount();
  }
}
