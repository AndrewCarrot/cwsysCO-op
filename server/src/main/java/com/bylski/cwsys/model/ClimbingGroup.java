package com.bylski.cwsys.model;

import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class ClimbingGroup extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime classTime;
    private int durationInMinutes;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClimbingGroupType climbingGroupType;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<Climber> climbers = new HashSet<>();

    @ManyToMany(mappedBy = "climbingGroupSet")
    @JsonIgnore
    private Set<Coach> coachSet = new HashSet<>();

    public ClimbingGroup(){}

    public ClimbingGroup(
            DayOfWeek dayOfWeek,
            LocalTime classTime,
            int durationInMinutes,
            String name,
            ClimbingGroupType climbingGroupType
    ) {
        this.dayOfWeek = dayOfWeek;
        this.classTime = classTime;
        this.durationInMinutes = durationInMinutes;
        this.name = name;
        this.climbingGroupType = climbingGroupType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClimbingGroup that = (ClimbingGroup) o;
        return dayOfWeek == that.dayOfWeek && Objects.equals(classTime, that.classTime) && Objects.equals(name, that.name) && climbingGroupType == that.climbingGroupType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, classTime, name, climbingGroupType);
    }
}
