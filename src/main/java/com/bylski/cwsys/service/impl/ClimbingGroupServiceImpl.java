package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.dto.ClimbingGroupDTOMapper;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.payload.ClimbingGroupPayload;
import com.bylski.cwsys.repository.ClimberRepository;
import com.bylski.cwsys.repository.ClimbingGroupRepository;
import com.bylski.cwsys.service.inf.ClimbingGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClimbingGroupServiceImpl implements ClimbingGroupService {

    private final ClimbingGroupRepository climbingGroupRepository;
    private final ClimberRepository climberRepository;
    private final ClimbingGroupDTOMapper mapper;

    public ClimbingGroupServiceImpl(ClimbingGroupRepository climbingGroupRepository, ClimberRepository climberRepository, ClimbingGroupDTOMapper mapper) {
        this.climbingGroupRepository = climbingGroupRepository;
        this.climberRepository = climberRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ClimbingGroupDTO> getGroups() {
        return climbingGroupRepository.findAll().stream().map(mapper).toList();
    }

    @Override
    public ClimbingGroupDTO getGroupById(Long id) {
        Optional<ClimbingGroupDTO> result = climbingGroupRepository.findById(id).map(mapper);
        if(result.isEmpty())
            throw new ResourceNotFoundException("Group","id",id);
        return result.get();
    }

    @Override
    public List<ClimbingGroupDTO> getGroupByType(ClimbingGroupType type) {
        return climbingGroupRepository.getClimbingGroupByClimbingGroupType(type).stream().map(mapper).toList();
    }

    @Override
    public void addGroup(ClimbingGroupPayload payload) {
        Optional<ClimbingGroup> result = climbingGroupRepository.getClimbingGroupByName(payload.name());
        if(result.isPresent())
            throw new ResourceAlreadyExistsException("Group","name", payload.name());
        climbingGroupRepository.save(new ClimbingGroup(
                payload.dayOfWeek(),
                payload.classTime(),
                payload.durationInMinutes(),
                payload.name(),
                payload.climbingGroupType()
        ));
    }

    @Override
    public void deleteGroup(Long groupId) {
        climbingGroupRepository.deleteById(groupId);
    }

    @Override
    public void addClimber(Long groupId, Long climberId) {
        ClimbingGroup group = climbingGroupRepository.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group","id",groupId));
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("Climber","id",climberId));
        group.getClimbers().add(climber);
        climber.getGroups().add(group);
        climbingGroupRepository.save(group);
    }

    @Override
    public void removeClimber(Long groupId, Long climberId) {
        ClimbingGroup group = climbingGroupRepository.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group","id",groupId));
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("Climber","id",climberId));
        group.getClimbers().remove(climber);
        climber.getGroups().remove(group);
        climbingGroupRepository.save(group);
    }
}
