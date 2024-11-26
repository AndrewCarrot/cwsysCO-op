package com.bylski.cwsys.service.inf;


import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.dto.CoachDTO;
import com.bylski.cwsys.model.dto.EventDTO;
import com.bylski.cwsys.model.payload.CoachPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CoachService {
    void addCoach(CoachPayload coachPayload) throws Exception;
    void deleteCoach(Long coachId);
    List<CoachDTO> getCoaches();
    List<EventDTO> getActiveEvents(Long coachId);
    Page<EventDTO> getPastEvents(Long coachId, LocalDate from, Pageable pageable);
    CoachDTO getCoachById(Long coachId);
    List<CoachDTO> getCoachByFirstName(String firstName);
    List<CoachDTO> getCoachByLastName(String lastName);
    List<ClimbingGroupDTO> getClimbingGroups(Long coachId);
    void updateCoachData(CoachDTO payload);
}
