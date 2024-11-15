package com.bylski.cwsys.model;

import com.bylski.cwsys.model.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Event extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfParticipants;
    private int numberOfCoaches;
    private int durationInMinutes;

    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "eventSet")
    @JsonIgnore
    private Set<Coach> coachSet = new HashSet<>();

    public Event(int numberOfParticipants,
                 int numberOfCoaches,
                 int durationInMinutes,
                 LocalDateTime dateTime,
                 EventType eventType,
                 String name) {
        this.numberOfParticipants = numberOfParticipants;
        this.numberOfCoaches = numberOfCoaches;
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.durationInMinutes = durationInMinutes;
        this.name = name;
    }

    public Event(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dateTime, event.dateTime) && eventType == event.eventType && Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, eventType, name);
    }
}
