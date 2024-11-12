package com.bylski.cwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Climber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String cardNumber;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    private String note;
    private String phoneNumber;
    private boolean multisport;

    @ElementCollection
    @CollectionTable(name = "climber_passes", joinColumns = @JoinColumn(name = "climber_id"))
    private Set<Pass> passes = new HashSet<>();

    @ManyToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "group_climbers",
            joinColumns = { @JoinColumn(name = "climber_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id" ) }
    )
    @JsonIgnore
    private Set<ClimbingGroup> groups = new HashSet<>();

    public Climber(){}
    public Climber(String cardNumber, String firstName, String lastName){
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Climber(Long id, String cardNumber, String firstName, String lastName){
        this.id = id;
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Climber climber = (Climber) o;
        return Objects.equals(cardNumber, climber.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNumber);
    }
}
