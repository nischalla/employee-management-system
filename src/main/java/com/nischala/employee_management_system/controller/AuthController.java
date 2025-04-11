package com.nischala.employee_management_system.controller;

import com.nischala.employee_management_system.dto.AuthRequest;
import com.nischala.employee_management_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    //  This controller handles login and generates JWT tokens with roles

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        // 1. Authenticate username + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        // 2. Assign roles
        List<String> roles = new ArrayList<>();
        if (request.getUsername().equals("admin")) {
            roles.add("ROLE_ADMIN");
        } else {
            roles.add("ROLE_USER");
        }

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(request.getUsername(), roles);

        // 4. Return token to client
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
