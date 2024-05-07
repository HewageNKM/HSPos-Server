package com.nadunkawishika.helloshoesapplicationserver.api.auth;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginRequest;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginResponse;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.RegisterRequest;
import com.nadunkawishika.helloshoesapplicationserver.service.UserService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class User {
    private final UserService userService;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(User.class);

    @PostMapping("/login")
    public LoginResponse authenticate(@Validated @RequestBody LoginRequest loginRequest) {
        LOGGER.info("User Login Request: {}", loginRequest);
        return userService.authenticate(loginRequest);
    }

    @GetMapping("/forgot-password/{email}")
    public void forgotPassword(@PathVariable @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email) {
        LOGGER.info("Forgot Password Request: {}", email);
        userService.forgotPassword(email);
    }

    @PostMapping
    public void register(@Validated @RequestBody RegisterRequest registerRequest) {
        LOGGER.info("User Register Request: {}", registerRequest);
        userService.register(registerRequest);
    }

    @PutMapping
    public void updatePassword(@Validated @RequestBody RegisterRequest registerRequest) {
        LOGGER.info("Update Password Request: {}", registerRequest);
        userService.updatePassword(registerRequest);
    }
}
