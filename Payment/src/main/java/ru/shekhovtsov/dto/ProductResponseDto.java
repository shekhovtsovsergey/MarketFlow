package ru.shekhovtsov.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private boolean reserved;
    private String message;
}