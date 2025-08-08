package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.response.*;
import com.swyp10.pinggyewang.service.ExcuseService;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping("/{excuseId}/image")
  public void excuseHasImage(@PathVariable final Long excuseId) {
    excuseService.updateImageStatus(excuseId);
  }

  @GetMapping("/stats/detail")
  public List<TargetSatisfactionResponse> getStatsDetail() {
    return excuseService.getSatisfactionByTarget();
  }

  @GetMapping("/stats/by-day-of-week")
  public ResponseEntity<List<DayOfWeekStatsResponse>> getExcuseCountByDayOfWeek() {
    List<DayOfWeekStatsResponse> response = excuseService.getExcuseCountByDayOfWeek();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{excuseId}/detail")
  public ResponseEntity<ExcuseDetailReponse> getExcuseDetail(@PathVariable final Long excuseId) {
    return ResponseEntity.ok(excuseService.getExcuseDetailbyExcuseId(excuseId));
  }
}
