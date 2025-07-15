package com.swyp10.pinggyewang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DashboardController {

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    // 가짜 통계 데이터 생성
    Map<String, Object> stats = new HashMap<>();
    stats.put("totalExcuses", 15847);
    stats.put("todayExcuses", 234);
    stats.put("avgCredibilityScore", 7.2);
    stats.put("avgResponseTime", 1247);
    stats.put("totalTokensUsed", 892341);
    stats.put("activeUsers", 3421);

    model.addAttribute("stats", stats);

    // 최근 변명 데이터 (가짜 데이터)
    List<Map<String, Object>> recentExcuses = Arrays.asList(
        createExcuseData(1, "죄송합니다. 예상치 못한 교통체증으로 인해 늦었습니다.", "회의 지각", "상사", "Work", 7.5),
        createExcuseData(2, "노트북이 갑자기 꺼져서 작업한 내용을 잃어버렸습니다.", "과제 마감일 놓침", "교수", "School", 6.8),
        createExcuseData(3, "반려동물이 갑자기 아파서 병원에 데려가야 했습니다.", "약속 취소", "친구", "Personal", 9.2)
    );

    model.addAttribute("recentExcuses", recentExcuses);

    // 카테고리 목록
    List<String> categories = Arrays.asList("Work", "School", "Personal", "Social", "Health");
    model.addAttribute("categories", categories);

    // 차트 데이터
    List<Map<String, Object>> dailyUsageData = Arrays.asList(
        createDailyData("01-08", 187),
        createDailyData("01-09", 234),
        createDailyData("01-10", 312),
        createDailyData("01-11", 278),
        createDailyData("01-12", 298),
        createDailyData("01-13", 345),
        createDailyData("01-14", 234)
    );
    model.addAttribute("dailyUsageData", dailyUsageData);

    List<Map<String, Object>> categoryData = Arrays.asList(
        createCategoryData("Work", 4521),
        createCategoryData("School", 3892),
        createCategoryData("Personal", 2847),
        createCategoryData("Social", 2156),
        createCategoryData("Health", 1847)
    );
    model.addAttribute("categoryData", categoryData);

    return "dashboard";
  }

  private Map<String, Object> createExcuseData(int id, String excuse, String situation, String target, String category, double credibilityScore) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", id);
    data.put("excuse", excuse);
    data.put("situation", situation);
    data.put("target", target);
    data.put("category", category);
    data.put("credibilityScore", credibilityScore);
    data.put("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return data;
  }

  private Map<String, Object> createDailyData(String date, int excuses) {
    Map<String, Object> data = new HashMap<>();
    data.put("date", date);
    data.put("excuses", excuses);
    return data;
  }

  private Map<String, Object> createCategoryData(String name, int value) {
    Map<String, Object> data = new HashMap<>();
    data.put("name", name);
    data.put("value", value);
    return data;
  }
}