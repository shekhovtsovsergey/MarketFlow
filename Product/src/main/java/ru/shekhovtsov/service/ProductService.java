package ru.shekhovtsov.service;


import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto reserveProduct(ProductRequestDto productRequestDto);
}
