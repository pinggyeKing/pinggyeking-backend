package com.swyp10.pinggyewang.dto.response;

public record ExcuseStatisticsResponse(
    Long totalExcuses,
    Double averageSatisfaction,
    Double regenerationRate,
    PeakTimeResponse peakTime
) {}