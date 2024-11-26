package com.bylski.cwsys.repository;

import com.bylski.cwsys.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select * from event where date_time > current_date()", nativeQuery = true)
    List<Event> getActiveEvents();

    @Query(value = "select * from event where date_time between :from and current_date()", nativeQuery = true)
    List<Event> getPastEvents(LocalDate from);
}
