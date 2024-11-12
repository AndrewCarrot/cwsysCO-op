package com.bylski.cwsys.repository;

import com.bylski.cwsys.model.ClimbingGroup;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClimbingGroupRepository extends JpaRepository<ClimbingGroup, Long> {
    List<ClimbingGroup> getClimbingGroupByClimbingGroupType(ClimbingGroupType climbingGroupType);

    Optional<ClimbingGroup> getClimbingGroupByName(String name);
}
