package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.enums.EventType;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private CoachRepository coachRepository;
    @Mock
    private  EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private Coach coach;
    private Event event;
    private Event event2;
    private Event event3;
    private ClimbingGroup climbingGroup;

    @BeforeEach
    public void setUp(){
        coach = new Coach("Marcin","Bylski","Bela");
        event = new Event(
                20,
                2,
                90,
                LocalDateTime.of(2024,11,28,16,0),
                EventType.GROUP,
                "grupa testowa nr1"

        );
        event2 = new Event(
                20,
                2,
                90,
                LocalDateTime.of(2024,11,28,16,30),
                EventType.GROUP,
                "grupa testowa nr2"

        );
        event3 = new Event(
                20,
                2,
                90,
                LocalDateTime.of(2024,11,28,16,15),
                EventType.GROUP,
                "grupa testowa nr3"

        );
        climbingGroup = new ClimbingGroup(
                DayOfWeek.THURSDAY,
                LocalTime.of(15,0),
                90,
                "D1",
                ClimbingGroupType.CHILDREN
        );





    }

    @AfterEach
    public void cleanUp(){

    }

    @Test
    void addEventToCoachWithCollidingClimbingGroupDateShouldThrowAnException() {

        coach.getClimbingGroupSet().add(climbingGroup);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(eventRepository.findById(anyLong())).willReturn(Optional.of(event));


        assertThrows(ResourceAlreadyExistsException.class,()->  eventService.addCoach(1L,1L) );
    }

    @Test
    void addEventToCoachEdgeCase(){
        coach.getClimbingGroupSet().add(climbingGroup);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(eventRepository.findById(anyLong())).willReturn(Optional.of(event2));

        assertDoesNotThrow(()->eventService.addCoach(1L,1L));
    }

    @Test
    void addEventToCoachWithCollidingEventDateShouldThrowAnException(){
        coach.getEventSet().add(event3);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(eventRepository.findById(anyLong())).willReturn(Optional.of(event));

        assertThrows(ResourceAlreadyExistsException.class,() -> eventService.addCoach(1L,1L) );
    }
}