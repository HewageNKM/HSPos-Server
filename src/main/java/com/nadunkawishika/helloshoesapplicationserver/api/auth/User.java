package com.nadunkawishika.helloshoesapplicationserver.api.auth;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginRequest;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginResponse;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.RegisterRequest;
import com.nadunkawishika.helloshoesapplicationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/users")
@RequiredArgsConstructor
@CrossOrigin
public class User {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.toString());
        return userService.authenticate(loginRequest);
    }

    @PostMapping
    public void register(@Validated @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PutMapping
    public void updatePassword(@Validated RegisterRequest registerRequest) {
        userService.updatePassword(registerRequest);
    }
}
