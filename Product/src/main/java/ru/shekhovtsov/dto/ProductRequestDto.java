package ru.shekhovtsov.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private Long clientId;
    private Long productId;
    private Integer quantity;
}