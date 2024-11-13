package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Climber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClimberDTOMapperTest {

    @InjectMocks
    ClimberDTOMapper mapper;

    Climber climber = new Climber(
                1L,
                        "Ryszard",
                        "Biały",
                        "email@wp.pl",
                        "692738635",
                LocalDate.of(1997,7,1)
        );
    Climber climber2 = new Climber(
                2L,
                        "Waran",
                        "Komodo",
                        "emailXxX@wp.pl",
                        "324484637",
                LocalDate.of(1995,4,20)
        );

    ClimberDTO climberDTO = new ClimberDTO(
            1L,
            "Ryszard",
            "Biały",
            "email@wp.pl",
            "692738635",
            LocalDate.of(1997,7,1),
            null,
            null,
            false,
            new HashSet<>(),
            new HashSet<>()

    );
    ClimberDTO climberDTO2 = new ClimberDTO(
            2L,
            "Waran",
            "Komodo",
            "emailXxX@wp.pl",
            "324484637",
            LocalDate.of(1995,4,20),
            null,
            null,
            false,
            new HashSet<>(),
            new HashSet<>()
    );

    @Test
    public void mappingTest(){
        assertEquals(climberDTO, mapper.apply(climber));
        assertEquals(climberDTO2, mapper.apply(climber2));
    }

}