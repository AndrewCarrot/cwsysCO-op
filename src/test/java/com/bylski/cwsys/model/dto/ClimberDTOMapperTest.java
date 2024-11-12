package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Climber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClimberDTOMapperTest {

    @InjectMocks
    ClimberDTOMapper mapper;

    Climber climber = new Climber(
            1L,
                "12123123",
                        "Ryszard",
                        "Biały"
    );
    Climber climber2 = new Climber(
            2L,
                "12121",
                        "Stachu",
                        "Jones"
    );

    ClimberDTO climberDTO = new ClimberDTO(
            1L,
            "12123123",
            "Ryszard",
            "Biały",
            null,
            null,
            false,
            new HashSet<>(),
            new HashSet<>());
    ClimberDTO climberDTO2 = new ClimberDTO(
            2L,
            "12121",
            "Stachu",
            "Jones",
            null,
            null,
            false,
            new HashSet<>(),
            new HashSet<>());

    @Test
    public void mappingTest(){
        assertEquals(climberDTO, mapper.apply(climber));
        assertEquals(climberDTO2, mapper.apply(climber2));
    }

}