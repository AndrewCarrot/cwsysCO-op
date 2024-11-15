package com.bylski.cwsys.model.dto;

import com.bylski.cwsys.model.ClimbingGroup;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClimbingGroupDTOMapper implements Function<ClimbingGroup,ClimbingGroupDTO> {
    @Override
    public ClimbingGroupDTO apply(ClimbingGroup climbingGroup) {
        return new ClimbingGroupDTO(
                climbingGroup.getId(),
                climbingGroup.getDayOfWeek(),
                climbingGroup.getClassTime(),
                climbingGroup.getDurationInMinutes(),
                climbingGroup.getName(),
                climbingGroup.getClimbingGroupType(),
                climbingGroup.getCreatedDate(),
                climbingGroup.getLastModifiedDate(),
                climbingGroup.getStartDate(),
                climbingGroup.getEndDate(),
                climbingGroup.getClimbers()
        );
    }
}
