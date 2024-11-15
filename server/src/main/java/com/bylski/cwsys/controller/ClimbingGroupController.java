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
            @ApiResponse(responseCode = "404", description = "Group with given ID does not exist in database",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
            })
    })
    @GetMapping("/{groupId}")
    public ClimbingGroupDTO getGroupById(
            @Parameter(name = "groupId", description = "ID of a group you want to retrieve")
            @PathVariable Long groupId
    ){
        return groupService.getGroupById(groupId);
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
            @ApiResponse(responseCode = "409", description = "Group with given name already exists in database")
    })
    @PostMapping("/new")
    public void addNewGroup(@RequestBody ClimbingGroupPayload groupPayload){
        groupService.addGroup(groupPayload);
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
            description = "Request Example: api/group/add-climber?groupId=1&climberId=1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Climber or Group not found with given id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
                    })
    })
    @Parameters({
            @Parameter(name = "groupId", description = "RequestParam"),
            @Parameter(name = "climberId", description = "RequestParam")
    })
    @PatchMapping("/add-climber")
    public void addClimber(@RequestParam Long groupId, @RequestParam Long climberId){
        groupService.addClimber(groupId,climberId);
    }

    @Operation(
            summary = "Remove climber from the group",
            description = "Request Example: api/group/remove-climber?groupId=1&climberId=1"
    )
    @Parameters({
            @Parameter(name = "groupId", description = "RequestParam"),
            @Parameter(name = "climberId", description = "RequestParam")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Climber or Group not found with given id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class))
                    })
    })
    @PatchMapping("/remove-climber")
    public void removeClimber(@RequestParam Long groupId, @RequestParam Long climberId){
        groupService.removeClimber(groupId,climberId);
    }


}
