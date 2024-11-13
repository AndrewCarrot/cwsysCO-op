package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.Climber;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClimberDTOMapper implements Function<Climber, ClimberDTO> {
    @Override
    public ClimberDTO apply(Climber climber) {
        return new ClimberDTO(
                climber.getId(),
                climber.getFirstName(),
                climber.getLastName(),
                climber.getEmail(),
                climber.getPhoneNumber(),
                climber.getDateOfBirth(),
                climber.getNote(),
                climber.getCardNumber(),
                climber.isMultisport(),
                climber.getPasses(),
                climber.getGroups());
    }
}
