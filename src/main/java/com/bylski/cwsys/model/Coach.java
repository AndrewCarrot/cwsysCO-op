package com.bylski.cwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String personalNumber;

    @ManyToMany(cascade =
            {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "Coach_event",
            joinColumns = { @JoinColumn(name = "coach_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    @JsonIgnore
    Set<Event> eventSet = new HashSet<>();

    public Coach(String firstName, String lastName, String personalNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
    }

    public Coach(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(firstName, coach.firstName) && Objects.equals(lastName, coach.lastName) && Objects.equals(personalNumber, coach.personalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, personalNumber);
    }
}
