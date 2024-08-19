package ru.shekhovtsov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private boolean reserved;
    private String message;
}