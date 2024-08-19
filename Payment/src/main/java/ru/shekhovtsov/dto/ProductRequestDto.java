package ru.shekhovtsov.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private Long clientId;
    private Long productId;
    private Integer quantity;
}