package ru.shekhovtsov.service;

import org.springframework.stereotype.Service;
import ru.shekhovtsov.dao.ProductDao;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductResponseDto reserveProduct(ProductRequestDto productRequestDto) {
      return productDao.reserveProduct(productRequestDto);
    }


}
