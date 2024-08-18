package ru.shekhovtsov.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.shekhovtsov.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}