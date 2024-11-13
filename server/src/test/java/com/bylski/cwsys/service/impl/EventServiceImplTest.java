package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.EventDTOMapper;
import com.bylski.cwsys.model.enums.EventType;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepo;
    @Mock
    private CoachRepository coachRepo;
    @Mock
    private EventDTOMapper mapper;

    @InjectMocks
    private EventServiceImpl service;

    @Captor
    private ArgumentCaptor<Event> captor;

    private Coach coach;
    private Event event;

    @BeforeEach
    void setUp() {
        coach = new Coach(
                "Stanisław",
                "Warszawa",
                "1"
        );
        event = new Event(
                15,
                2,
                LocalDateTime.now(),
                EventType.GROUP,
                90,
                "SP20"
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void getEventById() {
    }

    @Test
    void addEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void addCoach() {
        given(coachRepo.findById(anyLong())).willReturn(Optional.of(coach));
        given(eventRepo.findById(anyLong())).willReturn(Optional.of(event));

        service.addCoach(1L,1L);

        verify(eventRepo,times(1)).save(captor.capture());

        Event result = captor.getValue();

        assertThat(result.getCoachSet()).isEqualTo(Set.of(coach));

    }

    @Test
    void removeCoach() {
    }
}