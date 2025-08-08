package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.response.ExcuseStatisticsResponse;
import com.swyp10.pinggyewang.service.ExcuseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

  private final ExcuseService excuseService;

  public DashboardController(final ExcuseService excuseService) {
    this.excuseService = excuseService;
  }

  @GetMapping
  public ExcuseStatisticsResponse dashboard() {
    return excuseService.getStatistics();
  }
}