package com.bylski.cwsys.controller;

import com.bylski.cwsys.model.dto.ClimbingGroupDTO;
import com.bylski.cwsys.model.enums.ClimbingGroupType;
import com.bylski.cwsys.model.payload.ClimbingGroupPayload;
import com.bylski.cwsys.service.inf.ClimbingGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Climbing Group Controller", description = "Methods for ClimbingGroup API")
@RestController
@RequestMapping("/group")
public class ClimbingGroupController {
    private final ClimbingGroupService groupService;

    public ClimbingGroupController(ClimbingGroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Returns all climbing groups as a List")
    @GetMapping("/all")
    public List<ClimbingGroupDTO> getAllGroups(){
        return groupService.getGroups();
    }

    @Operation(summary = "Returns ClimbingGroup with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(
            @Parameter(name = "groupId", description = "ID of a group you want to retrieve")
            @PathVariable Long groupId
    ){
        ClimbingGroupDTO climbingGroupDTO;
        try {
            climbingGroupDTO = groupService.getGroupById(groupId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body(climbingGroupDTO);
    }

    @Operation(
            summary = "Returns groups based on given groupType",
            description = "Request Example:  api/group/type?group-type=CHILDREN"
    )
    @GetMapping("/type")
    public List<ClimbingGroupDTO> getGroupsByType(
            @Parameter(description = "RequestParam", examples = {
                    @ExampleObject("CHILDREN")
            })
            @RequestParam(name = "group-type") ClimbingGroupType groupType
    ){
        return groupService.getGroupByType(groupType);
    }

    @Operation(
            summary = "Add new Group",
            description = "You can add a new Group to the schedule eg. we hired new coach and are able to do additional" +
                    " class on monday cause we are so good and have no competition so we have 4 people to 1 place in a group"
    )
    @Parameters({
            @Parameter(name = "groupPayload", description = "RequestBody of a ClimbingGroupPayload")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/new")
    public ResponseEntity<?> addNewGroup(@RequestBody ClimbingGroupPayload groupPayload){
        try {
            groupService.addGroup(groupPayload);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("New group added successfully");
    }

    @Operation(summary = "Delete group with given ID")
    @DeleteMapping("/{groupId}")
    public void deleteGroup(
            @Parameter(name = "groupId", description = "ID of a group you want to delete", required = true)
            @PathVariable Long groupId
    ){
        groupService.deleteGroup(groupId);
    }

    @Operation(
            summary = "Add climber to existing group",
            description = "Request Example: api/group/add-climber?group-id=1&climber-id=1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @Parameters({
            @Parameter(name = "group-id", description = "RequestParam"),
            @Parameter(name = "climber-id", description = "RequestParam")
    })
    @PatchMapping("/add-climber")
    public ResponseEntity<?> addClimber(@RequestParam(name = "group-id") Long groupId, @RequestParam(name = "climber-id") Long climberId){
        try {
            groupService.addClimber(groupId, climberId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Climber added successfully");
    }

    @Operation(
            summary = "Remove climber from the group",
            description = "Request Example: api/group/remove-climber?groupId=1&climberId=1"
    )
    @Parameters({
            @Parameter(name = "group-id", description = "RequestParam"),
            @Parameter(name = "climber-id", description = "RequestParam")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PatchMapping("/remove-climber")
    public ResponseEntity<?> removeClimber(@RequestParam(name = "group-id") Long groupId, @RequestParam(name = "climber-id") Long climberId){
        try {
            groupService.removeClimber(groupId, climberId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Climber removed successfully");
    }

    @Operation(summary = "Add coach to group")
    @Parameters({
            @Parameter(name = "group-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/add-coach")
    public ResponseEntity<?> addCoach(@RequestParam(name = "group-id") Long groupId, @RequestParam(name = "coach-id") Long coachId){
        try {
            groupService.addCoach(groupId,coachId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Coach added successfully");
    }

    @Operation(summary = "remove coach from group")
    @Parameters({
            @Parameter(name = "group-id", description = "RequestParam"),
            @Parameter(name = "coach-id", description = "RequestParam")
    })
    @PatchMapping("/remove-coach")
    public ResponseEntity<?> removeCoach(@RequestParam(name = "group-id") Long groupId, @RequestParam(name = "coach-id") Long coachId){
        try {
            groupService.removeCoach(groupId, coachId);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Coach removed successfully");
    }

    @Operation(summary = "update climbing data", description = "any amount and combination of ClimbingGroupDTO payload fields, " +
            "pass only fields which you want to be updated")
    @Parameter(name = "payload", description = "RequestBody")
    @PatchMapping
    public ResponseEntity<?> updateClimbingGroupData(@RequestBody ClimbingGroupDTO payload){
        try {
            groupService.updateClimbingGroupData(payload);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Climbing group data updated successfully");
    }

}
