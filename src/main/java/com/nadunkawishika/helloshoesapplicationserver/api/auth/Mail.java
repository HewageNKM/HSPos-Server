package com.nadunkawishika.helloshoesapplicationserver.api.auth;

import com.nadunkawishika.helloshoesapplicationserver.service.MailService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/mail")
@CrossOrigin
@RequiredArgsConstructor
public class Mail {
    private final MailService mailService;

    @GetMapping("/otp/send/{email}")
    public void sendMail(@PathVariable @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email) {
        mailService.sendOTP(email);
    }

    @GetMapping("/otp/verify/{otp}")
    public ResponseEntity<Object> verifyMail(@PathVariable @Pattern(regexp = "^[0-9]{4}$") String otp) {
        return mailService.verifyOTP(otp);
    }
}
