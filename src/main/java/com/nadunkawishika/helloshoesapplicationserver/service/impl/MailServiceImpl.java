package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final Random random = new Random();
    private String otp = null;

    @Override
    public void sendOTP(String email) {
        otp = String.valueOf(this.random.nextInt(9999));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplyhelloshoes@gmail.com");
        message.setTo(email);
        message.setSubject("OTP");
        message.setText("Your OTP is " + otp);
        javaMailSender.send(message);
    }

    @Override
    public ResponseEntity<Object> verifyOTP(String otp) {
        if (otp.equals(this.otp)) {
            return ResponseEntity.ok("verified");
        } else {
            return ResponseEntity.ok("not-verified");
        }
    }
}
