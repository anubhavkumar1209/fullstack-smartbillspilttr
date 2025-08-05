
package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.request.LoginRequest;
import com.smartbillsplittr.dto.request.SignupRequest;
import com.smartbillsplittr.dto.response.JwtResponse;
import com.smartbillsplittr.dto.response.MessageResponse;

public interface AuthService {
    MessageResponse register(SignupRequest request);
    JwtResponse     authenticate(LoginRequest request);
}
