package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.dto.CoachDTOMapper;
import com.bylski.cwsys.repository.CoachRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoachServiceImplTest {

    @Mock
    private CoachRepository repo;
    @Mock
    private CoachDTOMapper mapper;
    @InjectMocks
    private CoachServiceImpl service;

    @Captor
    private ArgumentCaptor<Coach> coachArgumentCaptor;

    private Coach coach;
    private Coach coach2;
    private Coach coach3;

    @BeforeEach
    public void setup(){
        coach = new Coach("Marcin", "Bylski", "1");
        coach2 = new Coach("Adrian","Sochacki","2");
        coach3 = new Coach("Michał","Sochacki","3");
    }


    @Test
    void addCoachWithUniqueFirstAndLastName(){
        when(repo.findAllByLastName(anyString())).thenReturn(List.of());

        service.addCoach(coach);

        verify(repo, times(1)).save(coach);
    }


    @Test
    void addCoachWithExistingLastButNotFirstName(){
        when(repo.findAllByLastName(anyString())).thenReturn(List.of(coach,coach2));

        service.addCoach(coach3);

        verify(repo, times(1)).save(coach3);
    }


    @Test
    void addCoachWithExistingFirstAndLastNameShouldThrowException() {
        given(repo.findAllByLastName(anyString())).willReturn(List.of(coach));

        Exception e = assertThrows(ResourceAlreadyExistsException.class, () -> service.addCoach(coach));
        assertEquals("Coach already exists with first name & last name : 'Marcin Bylski'", e.getMessage());
    }


}