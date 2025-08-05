package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.request.CreateGroupRequest;
import com.smartbillsplittr.dto.response.GroupResponse;

import java.util.List;

public interface GroupService {

    GroupResponse createGroup(CreateGroupRequest request, Long creatorId);

    GroupResponse getGroupById(Long id);

    List<GroupResponse> getGroupsForUser(Long userId);

    void addMember(Long groupId, Long userId);

    void removeMember(Long groupId, Long userId);
}
