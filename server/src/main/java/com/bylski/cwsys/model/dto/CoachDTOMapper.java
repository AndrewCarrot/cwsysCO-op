package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Coach;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CoachDTOMapper implements Function<Coach, CoachDTO> {

    @Override
    public CoachDTO apply(Coach coach) {
        return new CoachDTO(
                coach.getId(),
                coach.getFirstName(),
                coach.getLastName(),
                coach.getPersonalNumber(),
                coach.getCreatedDate(),
                coach.getLastModifiedDate()
        );
    }
}
