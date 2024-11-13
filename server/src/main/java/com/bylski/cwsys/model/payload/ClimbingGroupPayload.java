package com.bylski.cwsys.model.payload;

import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.enums.DayOfWeek;

import java.time.LocalTime;

public record ClimbingGroupPayload(
       DayOfWeek dayOfWeek,
       LocalTime classTime,
       int durationInMinutes,
       String name,
       ClimbingGroupType climbingGroupType
) {}
