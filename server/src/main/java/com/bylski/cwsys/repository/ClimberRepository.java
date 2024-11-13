package com.bylski.cwsys.repository;

import com.bylski.cwsys.model.Climber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClimberRepository extends JpaRepository<Climber, Long> {
    Optional<Climber> findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
    boolean existsByEmail(String email);
}
