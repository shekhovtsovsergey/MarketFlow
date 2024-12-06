package ru.shekhovtsov.service;

import org.springframework.stereotype.Service;
import ru.shekhovtsov.dao.ProductRepository;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shekhovtsov.model.Product;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto reserveProduct(ProductRequestDto productRequestDto) {
        Long productId = productRequestDto.getProductId();
        Integer requestedQuantity = productRequestDto.getQuantity();

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            return new ProductResponseDto(productId, null, null, false, "Продукт не найден.");
        }

        Product product = optionalProduct.get();
        Integer availableQuantity = product.getQuantity();
        BigDecimal price = product.getPrice();

        if (availableQuantity < requestedQuantity) {
            return new ProductResponseDto(productId, availableQuantity, price.multiply(BigDecimal.valueOf(availableQuantity)), false, "Недостаточно продуктов на складе.");
        }

        product.setQuantity(availableQuantity - requestedQuantity);
        productRepository.save(product);

        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(requestedQuantity));
        return new ProductResponseDto(productId, requestedQuantity, totalPrice, true, "Продукт успешно зарезервирован.");
    }

    @Override
    public BigDecimal getProductPrice(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new IllegalArgumentException("Продукт не найден.");
        }
        Product product = optionalProduct.get();
        return product.getPrice();
    }

}