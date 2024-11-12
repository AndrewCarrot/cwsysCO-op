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
                climber.getCardNumber(),
                climber.getFirstName(),
                climber.getLastName(),
                climber.getNote(),
                climber.getPhoneNumber(),
                climber.isMultisport(),
                climber.getPasses(),
                climber.getGroups());
    }
}
