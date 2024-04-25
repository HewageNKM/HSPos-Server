package com.nadunkawishika.helloshoesapplicationserver.dto.resAndReq;

import com.nadunkawishika.helloshoesapplicationserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponse {
    private String token;
    private Collection<? extends GrantedAuthority> role;
}
