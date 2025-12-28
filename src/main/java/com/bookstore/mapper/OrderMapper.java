package com.bookstore.mapper;

import com.bookstore.dto.response.OrderResponseDto;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toResponseDto(Order order);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    OrderResponseDto.OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);
}
