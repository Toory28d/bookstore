package com.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDto> items;

    @Data
    public static class OrderItemRequestDto {
        private Long bookId;
        private Integer quantity;
    }
}
