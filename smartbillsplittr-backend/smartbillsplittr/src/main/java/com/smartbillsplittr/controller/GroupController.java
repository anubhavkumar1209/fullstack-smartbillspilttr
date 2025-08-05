package com.smartbillsplittr.controller;

import com.smartbillsplittr.dto.request.CreateGroupRequest;
import com.smartbillsplittr.dto.response.GroupResponse;
import com.smartbillsplittr.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smartbillsplittr.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    private Long currentUserId() {
        return ((UserPrincipal)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @Valid @RequestBody CreateGroupRequest request) {
        GroupResponse res = groupService.createGroup(request, currentUserId());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> listForUser() {
        return ResponseEntity.ok(groupService.getGroupsForUser(currentUserId()));
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<Void> addMember(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        groupService.addMember(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        groupService.removeMember(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}
