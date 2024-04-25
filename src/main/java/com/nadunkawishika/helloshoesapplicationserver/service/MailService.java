package com.nadunkawishika.helloshoesapplicationserver.service;

import com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq.MailRes;
import org.springframework.http.ResponseEntity;

public interface MailService {
    ResponseEntity<MailRes> sendOTP(String email);

    ResponseEntity<Object> verifyOTP(String otp);
}
