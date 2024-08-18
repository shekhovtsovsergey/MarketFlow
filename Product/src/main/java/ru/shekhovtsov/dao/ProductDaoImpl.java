package ru.shekhovtsov.dao;


import org.springframework.stereotype.Repository;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final DataSource dataSource;

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProductResponseDto reserveProduct(ProductRequestDto productRequestDto) {
        Long productId = productRequestDto.getProductId();
        Integer requestedQuantity = productRequestDto.getQuantity();
        String selectSql = "SELECT quantity, price FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setLong(1, productId);
            ResultSet rs = selectStatement.executeQuery();

            if (!rs.next()) {
                return new ProductResponseDto(productId, null, null, false, "Продукт не найден.");
            }

            Integer availableQuantity = rs.getInt("quantity");
            BigDecimal price = rs.getBigDecimal("price");

            if (availableQuantity < requestedQuantity) {
                return new ProductResponseDto(productId, availableQuantity, price.multiply(BigDecimal.valueOf(availableQuantity)), false, "Недостаточно продуктов на складе.");
            }

            String updateSql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setInt(1, requestedQuantity);
                updateStatement.setLong(2, productId);
                updateStatement.executeUpdate();
            }

            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(requestedQuantity));
            return new ProductResponseDto(productId, requestedQuantity, totalPrice, true, "Продукт успешно зарезервирован.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при резервировании продукта", e);
        }
    }
}
