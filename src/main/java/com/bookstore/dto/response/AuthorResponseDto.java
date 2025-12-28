package com.bookstore.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuthorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
