package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginRequest;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.LoginResponse;
import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.RegisterRequest;
import com.nadunkawishika.helloshoesapplicationserver.entity.Role;
import com.nadunkawishika.helloshoesapplicationserver.entity.User;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.AlreadyExistException;
import com.nadunkawishika.helloshoesapplicationserver.repository.UserRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.JWTService;
import com.nadunkawishika.helloshoesapplicationserver.service.MailService;
import com.nadunkawishika.helloshoesapplicationserver.service.UserService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public void register(RegisterRequest registerRequest) {
        Optional<User> byEmail = userRepository.findByEmail(registerRequest.getEmail().toLowerCase());
        if (byEmail.isPresent()) {
            LOGGER.error("Email already exists");
            throw new AlreadyExistException("Email already exists");
        } else {
            User user = User
                    .builder()
                    .email(registerRequest.getEmail())
                    .id(GenerateId.getId("USR"))
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole())
                    .build();
            userRepository.save(user);
            LOGGER.info("User Registered: {}", user);
            ResponseEntity.ok();
        }
    }

    @Override
    public void updatePassword(RegisterRequest registerRequest) {
        Optional<User> byEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            if(registerRequest.getRole() != null) {
                user.setRole(registerRequest.getRole());
            }
            userRepository.save(user);
            LOGGER.info("Password Updated: {}", user);
        } else {
            LOGGER.error("Email does not exist");
            throw new AlreadyExistException("Email does not exist");
        }
    }
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequest.getEmail().toLowerCase());
                LOGGER.info("User Authenticated: {}", loginRequest.getEmail().toLowerCase());
                LOGGER.info("Token: {}", token);
                return LoginResponse.builder().token(token).role(authenticate.getAuthorities()).build();
            } else {
                LOGGER.error("Invalid Credentials");
                throw new BadCredentialsException("Invalid Credentials");
            }
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage().toUpperCase());
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public void forgotPassword(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isPresent()) {
            LOGGER.info("Sending OTP to: {}", email);
            mailService.sendOTP(email);
        } else {
            LOGGER.error("User does not exist");
            throw new UsernameNotFoundException("User does not exist");
        }
    }
}
