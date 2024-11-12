package com.bylski.cwsys.service.inf;

import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.payload.ClimbingGroupPayload;

import java.util.List;

public interface ClimbingGroupService {
    List<ClimbingGroupDTO> getGroups();
    ClimbingGroupDTO getGroupById(Long id);
    List<ClimbingGroupDTO> getGroupByType(ClimbingGroupType type);
    void addGroup(ClimbingGroupPayload payload);
    void deleteGroup(Long groupId);
    void addClimber(Long groupId, Long climberId);
    void removeClimber(Long groupId, Long climberId);
}
