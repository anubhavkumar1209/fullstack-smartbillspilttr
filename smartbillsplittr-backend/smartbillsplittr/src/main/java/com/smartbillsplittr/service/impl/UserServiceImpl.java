package com.smartbillsplittr.service.impl;

import com.smartbillsplittr.dto.request.SignupRequest;
import com.smartbillsplittr.dto.response.UserResponse;
import com.smartbillsplittr.exception.ResourceNotFoundException;
import com.smartbillsplittr.model.Group;
import com.smartbillsplittr.model.User;
import com.smartbillsplittr.repository.GroupRepository;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository,
                           GroupRepository groupRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(SignupRequest request) {
        User user = new User(
                request.getUsername(),
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword)
                .stream()
                .map(u -> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, SignupRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void addUserToGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        group.getMembers().add(user);
        groupRepository.save(group);
    }
}
