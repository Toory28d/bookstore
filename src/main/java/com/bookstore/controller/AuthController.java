package com.bookstore.controller;

import com.bookstore.dto.request.ChangePasswordRequestDto;
import com.bookstore.dto.request.LoginRequestDto;
import com.bookstore.dto.request.RegisterRequestDto;
import com.bookstore.dto.request.UserRequestDto;
import com.bookstore.dto.response.AuthResponseDto;
import com.bookstore.dto.response.UserResponseDto;
import com.bookstore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserResponseDto> changePassword(@Valid @RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(authService.changePassword(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authService.updateProfile(request));
    }
}
