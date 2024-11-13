package com.bylski.cwsys.service.inf;


import com.bylski.cwsys.model.Coach;
import com.bylski.cwsys.model.Event;
import com.bylski.cwsys.model.dto.CoachDTO;

import java.util.List;
import java.util.Set;

public interface CoachService {
    void addCoach(Coach coach) throws Exception;
    void deleteCoach(Long coachId);
    List<CoachDTO> getCoaches();
    Set<Event> getEventsForGivenCoach(Long coachId);
    CoachDTO getCoachById(Long coachId);
    List<CoachDTO> getCoachByFirstName(String firstName);
    List<CoachDTO> getCoachByLastName(String lastName);
}