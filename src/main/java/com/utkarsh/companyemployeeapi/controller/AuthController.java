package com.utkarsh.companyemployeeapi.controller;

import com.utkarsh.companyemployeeapi.security.JwtTokenUtil;
import com.utkarsh.companyemployeeapi.security.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;


    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername().equals(USERNAME) && loginRequest.getPassword().equals(PASSWORD)) {
            String token = jwtTokenUtil.generatedToken(loginRequest.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
