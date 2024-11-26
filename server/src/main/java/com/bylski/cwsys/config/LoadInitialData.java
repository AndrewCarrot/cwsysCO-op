package com.bylski.cwsys.config;

import com.bylski.cwsys.model.*;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
//import com.bylski.cwsys.model.enums.DayOfWeek;
import com.bylski.cwsys.model.enums.EventType;
import com.bylski.cwsys.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Configuration
public class LoadInitialData {
    private final EventRepository eventRepository;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final ClimberRepository climberRepository;
    private final ClimbingGroupRepository climbingGroupRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${SYSTEM_USER}")
    private String SYSTEM_USER;
    @Value("${SYSTEM_PASSWORD}")
    private String SYSTEM_PASSWORD;
    @Value("${SYSTEM_EMAIL}")
    private String SYSTEM_EMAIL;

    public LoadInitialData(
            EventRepository eventRepository,
            CoachRepository coachRepository,
            ClimberRepository climberRepository,
            UserRepository userRepository,
            ClimbingGroupRepository climbingGroupRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.eventRepository = eventRepository;
        this.coachRepository = coachRepository;
        this.userRepository = userRepository;
        this.climbingGroupRepository = climbingGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.climberRepository = climberRepository;
    }

    @Bean
    public CommandLineRunner loadData(){
        return (args)->{
            Coach coach1 = new Coach("Ryszard", "Suchomski","123423");
            Coach coach2 = new Coach("Adam", "Wysocki","3243443");
            Coach coach3 = new Coach("Ania", "Konradzka","43242343");

            Climber climber = new Climber("Marcin","Bylski","email@wp.pl","692783635", LocalDate.of(1997,7,1));
            climber.setCardNumber("696969");
            Event event1 = new Event(
                    15,
                    2,
                    90,
                    LocalDateTime.now().plusDays(1),
                    EventType.GROUP,
                    "Aspi"
            );

            Event event2 = new Event(
              20,
              3,
                    90,
              LocalDateTime.now().plusMinutes(15),
              EventType.GROUP,
                    "fundacja xyz"
            );

            Event pastEvent = new Event(
                    20,
                    2,
                    90,
                    LocalDateTime.now().minusMinutes(100),
                    EventType.GROUP,
                    "grupa testowa nr4"

            );

            Event pastEvent2 = new Event(
                    20,
                    2,
                    90,
                    LocalDateTime.now().minusDays(1),
                    EventType.GROUP,
                    "grupa testowa nr5"

            );

            Event pastEvent3 = new Event(
                    20,
                    2,
                    90,
                    LocalDateTime.now().minusMonths(1),
                    EventType.GROUP,
                    "grupa testowa nr6"

            );

            ClimbingGroup group1 = new ClimbingGroup(
                    DayOfWeek.MONDAY,
                    LocalTime.now(),
                    90,
                    "grupa specjalna",
                    ClimbingGroupType.BEGINNERS
            );

            climbingGroupRepository.save(group1);


            Set<Event> eventSet = Set.of(event1, event2, pastEvent, pastEvent2, pastEvent3);
            coach1.setEventSet(eventSet);

            Set<Coach> coachSet = Set.of(coach1, coach2, coach3);

            coachSet.forEach(coachRepository::save);
            eventSet.forEach(eventRepository::save);



           userRepository.save(new User(SYSTEM_USER, passwordEncoder.encode(SYSTEM_PASSWORD), SYSTEM_EMAIL));
           climberRepository.save(climber);
        };
    }
}
