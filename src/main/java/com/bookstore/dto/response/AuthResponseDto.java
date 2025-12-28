package com.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private UserResponseDto user;

    public AuthResponseDto(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }
}
