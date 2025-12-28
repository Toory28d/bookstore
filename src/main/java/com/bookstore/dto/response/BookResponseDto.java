package com.bookstore.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer publicationYear;
    private AuthorResponseDto author;
    private CategoryResponseDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
