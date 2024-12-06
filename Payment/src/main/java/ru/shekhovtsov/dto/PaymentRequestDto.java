package ru.shekhovtsov.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Long clientId;
    private Long productId;
    private Integer quantity;
}