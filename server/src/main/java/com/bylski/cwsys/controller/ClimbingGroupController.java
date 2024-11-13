package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.payload.ClimbingGroupPayload;
import com.bylski.cwsys.service.inf.ClimbingGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class ClimbingGroupController {
    private final ClimbingGroupService groupService;

    public ClimbingGroupController(ClimbingGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public List<ClimbingGroupDTO> getAllGroups(){
        return groupService.getGroups();
    }

    @GetMapping("/{groupId}")
    public ClimbingGroupDTO getGroupById(@PathVariable Long groupId){
        return groupService.getGroupById(groupId);
    }

    @GetMapping("/type")
    public List<ClimbingGroupDTO> getGroupsByType(@RequestParam ClimbingGroupType groupType){
        return groupService.getGroupByType(groupType);
    }

    @PostMapping("/new")
    public void addNewGroup(@RequestBody ClimbingGroupPayload groupPayload){
        groupService.addGroup(groupPayload);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
    }

    @PatchMapping("/add-climber")
    public void addClimber(@RequestParam Long groupId, @RequestParam Long climberId){
        groupService.addClimber(groupId,climberId);
    }

    @PatchMapping("/remove-climber")
    public void removeClimber(@RequestParam Long groupId, @RequestParam Long climberId){
        groupService.removeClimber(groupId,climberId);
    }


}
