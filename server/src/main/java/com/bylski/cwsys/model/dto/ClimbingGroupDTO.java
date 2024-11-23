package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.DayOfWeek;
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
         @JsonIgnoreProperties("groups")
         Set<Climber> climbers,
         Set<Coach> coaches
) {}
