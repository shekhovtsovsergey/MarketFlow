package ru.shekhovtsov.dao;


import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;

public interface ProductDao {

    ProductResponseDto reserveProduct(ProductRequestDto productRequestDto);

}
