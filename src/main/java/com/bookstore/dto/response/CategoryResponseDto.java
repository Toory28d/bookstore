package com.bookstore.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
