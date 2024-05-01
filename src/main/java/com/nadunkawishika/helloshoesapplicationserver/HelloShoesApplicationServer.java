package com.nadunkawishika.helloshoesapplicationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalAuthentication
public class HelloShoesApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(HelloShoesApplicationServer.class, args);
    }
}
