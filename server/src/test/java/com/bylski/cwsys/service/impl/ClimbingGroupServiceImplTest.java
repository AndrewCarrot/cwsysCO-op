package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.enums.EventType;
import com.bylski.cwsys.repository.ClimbingGroupRepository;
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
class ClimbingGroupServiceImplTest {

    @Mock
    private CoachRepository coachRepository;
    @Mock
    private ClimbingGroupRepository climbingGroupRepository;

    @InjectMocks
    private ClimbingGroupServiceImpl climbingGroupService;

    private Coach coach;
    private Event event;
    private ClimbingGroup climbingGroup;
    private ClimbingGroup climbingGroup2;
    private ClimbingGroup climbingGroup3;

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

        climbingGroup = new ClimbingGroup(
                DayOfWeek.THURSDAY,
                LocalTime.of(15,0),
                90,
                "D1",
                ClimbingGroupType.CHILDREN
        );
        climbingGroup2 = new ClimbingGroup(
                DayOfWeek.THURSDAY,
                LocalTime.of(13,45),
                90,
                "D1",
                ClimbingGroupType.CHILDREN
        );
        climbingGroup3 = new ClimbingGroup(
                DayOfWeek.THURSDAY,
                LocalTime.of(13,30),
                90,
                "D1",
                ClimbingGroupType.CHILDREN
        );


    }

    @AfterEach
    public void cleanUp(){

    }

    @Test
    void addCoachToClimbingGroupWithCollidingEventDateShouldNotThrowException() {

        coach.getEventSet().add(event);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(climbingGroup));


        assertDoesNotThrow(()->  climbingGroupService.addCoach(1L,1L) );
    }

    @Test
    void addCoachToClimbingGroupEdgeCase(){
        coach.getClimbingGroupSet().add(climbingGroup3);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(climbingGroup));

        assertDoesNotThrow(()->climbingGroupService.addCoach(1L,1L));
    }

    @Test
    void addCoachToClimbingGroupWithCollidingDateShouldThrowException(){
        coach.getClimbingGroupSet().add(climbingGroup2);

        given(coachRepository.findById(anyLong())).willReturn(Optional.ofNullable(coach));
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(climbingGroup));

        assertThrows(ResourceAlreadyExistsException.class,() -> climbingGroupService.addCoach(1L,1L) );
    }
}