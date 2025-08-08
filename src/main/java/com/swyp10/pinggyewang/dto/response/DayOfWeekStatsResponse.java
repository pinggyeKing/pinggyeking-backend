package com.swyp10.pinggyewang.dto.response;

public record DayOfWeekStatsResponse(
    String dayOfWeek,
    String dayOfWeekEn,
    Long totalCount
) {
  public static DayOfWeekStatsResponse of(int dayOfWeekNumber, Long count, Double avgSatisfaction) {
    String[] koreanDays = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
    String[] englishDays = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

    // 일요일 - 1 / 토요일 - 7
    int index = dayOfWeekNumber - 1;

    return new DayOfWeekStatsResponse(
        koreanDays[index],
        englishDays[index],
        count
    );
  }

  public static DayOfWeekStatsResponse of(int dayOfWeekNumber, Long count) {
    return of(dayOfWeekNumber, count, null);
  }
}
