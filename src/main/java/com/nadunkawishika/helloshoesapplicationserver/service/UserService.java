package com.nadunkawishika.helloshoesapplicationserver.service;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginRequest;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginResponse;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void register(RegisterRequest registerRequest);

    void updatePassword(RegisterRequest registerRequest);
    ResponseEntity<LoginResponse> authenticate(LoginRequest loginRequest);

}
