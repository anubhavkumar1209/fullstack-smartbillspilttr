package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.request.SignupRequest;
import com.smartbillsplittr.dto.response.UserResponse;
import com.smartbillsplittr.model.User;

import java.util.List;

public interface UserService {

    UserResponse registerUser(SignupRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> searchUsers(String keyword);

    UserResponse updateUser(Long id, SignupRequest request);

    void deleteUser(Long id);

    void addUserToGroup(Long userId, Long groupId);
}
