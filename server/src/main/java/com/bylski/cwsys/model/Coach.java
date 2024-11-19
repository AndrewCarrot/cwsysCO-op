package com.bylski.cwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Coach extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String pseudonym;

    @ManyToMany(cascade =
            {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "coach_event",
            joinColumns = { @JoinColumn(name = "coach_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    @JsonIgnore
    private Set<Event> eventSet = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "coach_climbing_group",
            joinColumns = { @JoinColumn(name = "coach_id") },
            inverseJoinColumns = { @JoinColumn(name = "climbing_group_id") }
    )
    @JsonIgnore
    private Set<ClimbingGroup> climbingGroupSet = new HashSet<>();

    public Coach(String firstName, String lastName, String pseudonym){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudonym = pseudonym;
    }

    public Coach(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(firstName, coach.firstName) && Objects.equals(lastName, coach.lastName) && Objects.equals(pseudonym, coach.pseudonym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, pseudonym);
    }
}
