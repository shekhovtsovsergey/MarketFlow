package ru.shekhovtsov.dao;


import ru.shekhovtsov.model.Product;

import java.util.List;

public interface ProductDao {

    void createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductByUserId(Long id);
    void updateProduct(Product product);
    void deleteProduct(Long id);

}
