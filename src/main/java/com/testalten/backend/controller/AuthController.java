package com.testalten.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testalten.backend.dto.CreateUserDTO;
import com.testalten.backend.dto.LoginUserDTO;
import com.testalten.backend.entity.User;
import com.testalten.backend.security.JwtUtil;
import com.testalten.backend.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/account")
    public ResponseEntity<?> register(@RequestBody CreateUserDTO dto) {
        try {
            User user = userService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO dto) {
        try {
            String token = userService.authenticate(dto, jwtUtil);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }
}
