package com.bylski.cwsys.service.impl;

import com.bylski.cwsys.exception.ResourceAlreadyExistsException;
import com.bylski.cwsys.exception.ResourceNotFoundException;
import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.payload.ClimbingGroupPayload;
import com.bylski.cwsys.repository.ClimberRepository;
import com.bylski.cwsys.repository.ClimbingGroupRepository;
import com.bylski.cwsys.repository.CoachRepository;
import com.bylski.cwsys.service.inf.ClimbingGroupService;
import com.bylski.cwsys.service.inf.CoachService;
import com.bylski.cwsys.utilz.Patcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClimbingGroupServiceImpl implements ClimbingGroupService {

    private final ClimbingGroupRepository climbingGroupRepository;
    private final ClimberRepository climberRepository;
    private final CoachRepository coachRepository;
    private final CoachService coachService;
    private final ObjectMapper objectMapper;

    public ClimbingGroupServiceImpl(
            ClimbingGroupRepository climbingGroupRepository,
            ClimberRepository climberRepository,
            CoachRepository coachRepository, CoachService coachService,
            ObjectMapper objectMapper
    ) {
        this.climbingGroupRepository = climbingGroupRepository;
        this.climberRepository = climberRepository;
        this.coachRepository = coachRepository;
        this.coachService = coachService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ClimbingGroupDTO> getGroups() {
        return climbingGroupRepository
                .findAll()
                .stream()
                .map(o->objectMapper.convertValue(o,ClimbingGroupDTO.class))
                .toList();
    }

    @Override
    public ClimbingGroupDTO getGroupById(Long id) {
        Optional<ClimbingGroup> result = climbingGroupRepository.findById(id);

        if(result.isEmpty())
            throw new ResourceNotFoundException("Group","id",id);

        return objectMapper.convertValue(result.get(),ClimbingGroupDTO.class);
    }

    @Override
    public List<ClimbingGroupDTO> getGroupByType(ClimbingGroupType type) {
        return climbingGroupRepository
                .getClimbingGroupByClimbingGroupType(type)
                .stream()
                .map(o->objectMapper.convertValue(o,ClimbingGroupDTO.class))
                .toList();
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
        climberRepository.save(climber);
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

    @Override
    public List<EventDTO> addCoach(Long groupId, Long coachId){
        ClimbingGroup group = climbingGroupRepository.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group","id",groupId));
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));


        // check if coach doesn't have another group at that time
        //-------------------------------------

        LocalTime groupTime = group.getClassTime();
        DayOfWeek groupDay = group.getDayOfWeek();
        int groupDuration = group.getDurationInMinutes();

        //groups
        Optional<ClimbingGroup> optionalClimbingGroup = coach.getClimbingGroupSet()
                .stream()
                .filter(c -> c.getDayOfWeek().equals(groupDay))
                .filter( c -> {
                    LocalTime time = c.getClassTime();
                    int duration = c.getDurationInMinutes();
                    return groupTime.isBefore(time) && groupTime.plusMinutes(groupDuration).isAfter(time) ||
                            time.isBefore(groupTime) && time.plusMinutes(duration).isAfter(groupTime) ||
                            groupTime.equals(time);
                })
                .findAny();

        if (optionalClimbingGroup.isPresent())
            throw new ResourceAlreadyExistsException("Coach already has group assigned at given time");


        // if there are colliding events we return List of colliding events
        List<EventDTO> collidingEvents = coachService.getActiveEvents(coachId)
                .stream()
                .filter(e -> e.dateTime().getDayOfWeek().equals(groupDay))
                .filter( e -> {
                    LocalTime time = e.dateTime().toLocalTime();
                    int duration = e.durationInMinutes();
                    return groupTime.isBefore(time) && groupTime.plusMinutes(groupDuration).isAfter(time) ||
                            time.isBefore(groupTime) && time.plusMinutes(duration).isAfter(groupTime) ||
                            groupTime.equals(time);
                })
                        .toList();
        //-----------------------------------------------

        group.getCoachSet().add(coach);
        coach.getClimbingGroupSet().add(group);
        climbingGroupRepository.save(group);

        return collidingEvents;
    }

    @Override
    public void removeCoach(Long groupId, Long coachId) {
        ClimbingGroup group = climbingGroupRepository.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group","id",groupId));
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(()->new ResourceNotFoundException("Coach","id",coachId));

        group.getCoachSet().remove(coach);
        coach.getClimbingGroupSet().remove(group);
        climbingGroupRepository.save(group);
    }

    @Override
    public void updateClimbingGroupData(ClimbingGroupDTO payload) {
        ClimbingGroup existing = climbingGroupRepository.findById(payload.id())
                .orElseThrow(()->new ResourceNotFoundException("Climbing group","id",payload.id()));

        ClimbingGroup incomplete = objectMapper.convertValue(payload, ClimbingGroup.class);

        try{
            Patcher.objectPatcher(existing,incomplete);
            climbingGroupRepository.save(existing);
        }catch(IllegalAccessException e){
            e.getCause();
        }
    }
}
