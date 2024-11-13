package com.bylski.cwsys.config;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.enums.EventType;
import com.bylski.cwsys.repository.ClimberRepository;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
public class LoadInitialData {
    private final EventRepository eventRepository;
    private final CoachRepository coachRepository;

    public LoadInitialData(EventRepository eventRepository, CoachRepository coachRepository, ClimberRepository climberRepository) {
        this.eventRepository = eventRepository;
        this.coachRepository = coachRepository;
    }

    @Bean
    public CommandLineRunner loadData(){
        return (args)->{
            Coach coach1 = new Coach("Ryszard", "Suchomski","123423");
            Coach coach2 = new Coach("Adam", "Wysocki","3243443");
            Coach coach3 = new Coach("Ania", "Konradzka","43242343");

            Event event1 = new Event(
                    15,
                    2,
                    LocalDateTime.now(),
                    EventType.GROUP,
                    90,
                    "Aspi"
            );

            Event event2 = new Event(
              20,
              3,
              LocalDateTime.of(2024,11,16,10,0),
              EventType.GROUP,
              90,
                    "fundacja xyz"
            );

            coach1.getEventSet().add(event1);



            Set<Event> eventSet = Set.of(event1, event2);
            Set<Coach> coachSet = Set.of(coach1, coach2, coach3);

            coachSet.forEach(coachRepository::save);
            eventSet.forEach(eventRepository::save);


        };
    }
}
