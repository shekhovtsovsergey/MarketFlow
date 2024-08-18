package ru.shekhovtsov.controller;

import org.springframework.web.bind.annotation.*;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;
import ru.shekhovtsov.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/reserve")
    public ProductResponseDto reserveProduct(@RequestBody ProductRequestDto productRequestDto) {
        System.out.println("Received request to reserve product: {}" + productRequestDto);
        ProductResponseDto response = productService.reserveProduct(productRequestDto);
        System.out.println("Response for product reservation: {}" +  response);
        return response;
    }
}
