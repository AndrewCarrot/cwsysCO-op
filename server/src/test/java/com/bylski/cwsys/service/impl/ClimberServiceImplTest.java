package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.Pass;
import com.bylski.cwsys.model.dto.ClimberDTO;
import com.bylski.cwsys.model.dto.ClimberDTOMapper;
import com.bylski.cwsys.model.enums.ClassFrequency;
import com.bylski.cwsys.model.enums.PassType;
import com.bylski.cwsys.model.payload.ClimberPayload;
import com.bylski.cwsys.repository.ClimberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClimberServiceImplTest {

    @Mock
    private ClimberRepository repo;
    @Mock
    private ClimberDTOMapper mockMapper;
    @InjectMocks
    private ClimberServiceImpl service;

    @Captor
    private ArgumentCaptor<Climber> climberArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;


    private ClimberDTOMapper mapper;
    private Climber climber;
    private Climber climber2;
    private ClimberDTO climberDTO;
    private ClimberDTO climberDTO2;
    private Pass punchPass;
    private Pass classPass;



    @BeforeEach
    void setUp() {
        mapper = new ClimberDTOMapper();
        climber = new Climber(
                1L,
                "Ryszard",
                "Biały",
                "email@wp.pl",
                "692738635",
                LocalDate.of(1997,7,1)
        );
        climber2 = new Climber(
                2L,
                "Stanisław",
                "Wasilewski",
                "email2@wp.pl",
                "378564736",
                LocalDate.of(1995,4,20)
        );
        climberDTO = mapper.apply(climber);
        climberDTO2 = mapper.apply(climber2);


        punchPass = new Pass(
                PassType.PUNCH,
                false,
                8,
                null,
                null,
                null
        );

        classPass = new Pass(
                PassType.CLASS,
                false,
                8,
                LocalDate.of(2024,10,11),
                LocalDate.of(2024,11,9),
                ClassFrequency.TWICE_PER_WEEK
        );
    }

    @AfterEach
    void tearDown() {
        // niepotrzebne | dobra praktyka (?)
        Mockito.reset(repo);
        Mockito.reset(mockMapper);
    }


    @Test
    void getAllClimbers() {

        given(mockMapper.apply(climber)).willReturn(climberDTO);
        given(mockMapper.apply(climber2)).willReturn(climberDTO2);
        given(repo.findAll(Pageable.unpaged())).willReturn(new PageImpl<>(List.of(climber,climber2)));

        Page<ClimberDTO> result = service.getAllClimbers(Pageable.unpaged());

        assertThat(result.getSize()).isEqualTo(2);

        assertThat(result.stream().toList()).isEqualTo(Arrays.asList(climberDTO,climberDTO2));

    }

    @Test
    void getClimberByCardNumber() {

        given(mockMapper.apply(climber)).willReturn(climberDTO);
        given(repo.findByCardNumber(anyString())).willReturn(Optional.of(climber));

        ClimberDTO result = service.getClimberByCardNumber("12121");

        verify(repo,times(1)).findByCardNumber(anyString());

        assertThat(result).isEqualTo(climberDTO);

    }

    @Test
    void addNewClimber() {

        ClimberPayload payload = new ClimberPayload("Janusz","Sram", "sram@wp.pl","478647532",LocalDate.of(1965,10,14));
        given(repo.existsByEmail(anyString())).willReturn(false);

        service.addNewClimber(payload);

        verify(repo,times(1)).save(climberArgumentCaptor.capture());
        assertThat(climberArgumentCaptor.getValue().getEmail()).isEqualTo(payload.email());

    }

    @Test
    void deleteClimber() {

        service.deleteClimber(26L);

        verify(repo,times(1)).deleteById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(26L);

    }

    @Test
    void addNewPass() {
        given(repo.findById(anyLong())).willReturn(Optional.of(climber));

        service.addNewPass(1L, punchPass);

        verify(repo,times(1)).save(climberArgumentCaptor.capture());

        Climber result = climberArgumentCaptor.getValue();
        assertThat(result.getPasses().size()).isEqualTo(1);
        assertThat(result.getPasses().stream().findFirst()).isEqualTo(Optional.of(punchPass));

    }

    @Test
    void addExistingPassShouldThrowException(){
        climber.getPasses().add(punchPass);

        given(repo.findById(anyLong())).willReturn(Optional.of(climber));

        Throwable thrown = assertThrowsExactly(
                ResourceAlreadyExistsException.class,
                ()-> service.addNewPass(1L, punchPass)
        );

        assertEquals(thrown.getMessage(), "climber entity with id: " + climber.getId() + " already contains pass with this type");
    }

    @Test
    void addPassWithDifferentTypeThanExisting(){
        climber.getPasses().add(punchPass);

        given(repo.findById(anyLong())).willReturn(Optional.of(climber));

        service.addNewPass(1L, classPass);

        verify(repo,times(1)).save(climberArgumentCaptor.capture());

        Climber result = climberArgumentCaptor.getValue();
        assertThat(result.getPasses()).isEqualTo(Set.of(punchPass,classPass));

    }
}