package com.bylski.cwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Climber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NaturalId
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    @Pattern(regexp = "[0-9]{9}")
    private String phoneNumber;
    private String note;
    private String cardNumber;
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
    public Climber(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth =  dateOfBirth;
    }
    public Climber(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth =  dateOfBirth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Climber climber = (Climber) o;
        return Objects.equals(email, climber.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
