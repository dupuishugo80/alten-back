package com.testalten.backend.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.testalten.backend.dto.CreateUserDTO;
import com.testalten.backend.dto.LoginUserDTO;
import com.testalten.backend.entity.User;
import com.testalten.backend.repository.UserRepository;
import com.testalten.backend.security.JwtUtil;
import com.testalten.backend.security.SecurityUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public UserService(UserRepository userRepository, SecurityUtil securityUtil) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public User register(CreateUserDTO dto) throws NoSuchAlgorithmException {
        if (userRepository.findByEmail(dto.email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setUsername(dto.username);
        user.setFirstname(dto.firstname);
        user.setEmail(dto.email);
        user.setPassword(securityUtil.encode(dto.password));

        return userRepository.save(user);
    }

    public String authenticate(LoginUserDTO dto, JwtUtil jwtUtil) throws NoSuchAlgorithmException {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!securityUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
