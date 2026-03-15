package com.management.order.controller;

import com.management.order.security.JwtUtil;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String role) {

        return jwtUtil.generateToken(username, role);
    }
}