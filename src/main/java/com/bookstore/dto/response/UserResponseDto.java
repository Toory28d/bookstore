package com.bookstore.dto.response;

import com.bookstore.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
