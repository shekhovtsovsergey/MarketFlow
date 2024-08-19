package ru.shekhovtsov.service;


import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponseDto reserveProduct(ProductRequestDto productRequestDto);
    BigDecimal getProductPrice(Long productId);
}
