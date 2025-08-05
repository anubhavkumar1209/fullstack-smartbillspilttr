package com.smartbillsplittr.service.impl;

import com.smartbillsplittr.dto.request.CreateGroupRequest;
import com.smartbillsplittr.dto.response.GroupResponse;
import com.smartbillsplittr.exception.ResourceNotFoundException;
import com.smartbillsplittr.model.Group;
import com.smartbillsplittr.model.User;
import com.smartbillsplittr.repository.GroupRepository;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    public GroupServiceImpl(GroupRepository groupRepository,
                            UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GroupResponse createGroup(CreateGroupRequest request, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", creatorId));

        Group group = new Group(request.getName(), request.getDescription(), creator);
        group.getMembers().add(creator);

        return mapper.map(groupRepository.save(group), GroupResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", id));
        return mapper.map(group, GroupResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> getGroupsForUser(Long userId) {
        return groupRepository.findGroupsByUserId(userId)
                .stream()
                .map(g -> mapper.map(g, GroupResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addMember(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        group.getMembers().add(user);
        groupRepository.save(group);
    }

    @Override
    public void removeMember(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        group.getMembers().removeIf(u -> u.getId().equals(userId));
        groupRepository.save(group);
    }
}
