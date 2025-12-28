package com.bookstore.dto.response;

import com.bookstore.entity.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private Order.OrderStatus status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private List<OrderItemResponseDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class OrderItemResponseDto {
        private Long id;
        private Long bookId;
        private String bookTitle;
        private Integer quantity;
        private BigDecimal price;
    }
}
