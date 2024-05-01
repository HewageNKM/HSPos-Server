package com.nadunkawishika.helloshoesapplicationserver.api.auth;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginRequest;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginResponse;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.RegisterRequest;
import com.nadunkawishika.helloshoesapplicationserver.service.UserService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/users")
@RequiredArgsConstructor
public class User {
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse authenticate(@Validated @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.toString());
        return userService.authenticate(loginRequest);
    }

    @GetMapping("/forgot-password/{email}")
    public void forgotPassword(@PathVariable @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email) {
        userService.forgotPassword(email);
    }

    @PostMapping
    public void register(@Validated @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PutMapping
    public void updatePassword(@Validated @RequestBody RegisterRequest registerRequest) {
        userService.updatePassword(registerRequest);
    }
}
