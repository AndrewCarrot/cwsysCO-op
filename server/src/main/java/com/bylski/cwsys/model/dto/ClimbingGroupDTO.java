package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.enums.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public record ClimbingGroupDTO(
         Long id,
         DayOfWeek dayOfWeek,
         LocalTime classTime,
         int durationInMinutes,
         String name,
         ClimbingGroupType climbingGroupType,
         LocalDateTime createdDate,
         LocalDateTime lastModifiedDate,
         LocalDate startDate,
         LocalDate endDate,
         Set<Climber> climbers
) {}
