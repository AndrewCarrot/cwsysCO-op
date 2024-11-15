package com.bylski.cwsys.repository;

import com.bylski.cwsys.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach,Long> {
    List<Coach> findAllByLastName(String lastName);
    List<Coach> findAllByFirstName(String firstName);
    Optional<Coach> findByPersonalNumber(String personalNumber);
}
