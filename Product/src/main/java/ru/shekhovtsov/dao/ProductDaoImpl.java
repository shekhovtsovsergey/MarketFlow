package ru.shekhovtsov.dao;


import org.springframework.stereotype.Repository;
import ru.shekhovtsov.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final DataSource dataSource;

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createProduct(Product product) {
        String sql = "INSERT INTO products (user_id, account_number, balance, product_type) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, product.getUserId());
            statement.setString(2, product.getAccountNumber());
            statement.setBigDecimal(3, product.getBalance());
            statement.setString(4, product.getProductType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating product", e);
        }
    }

    @Override
    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("account_number"),
                            resultSet.getBigDecimal("balance"),
                            resultSet.getString("product_type")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting product by id", e);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("account_number"),
                            resultSet.getBigDecimal("balance"),
                            resultSet.getString("product_type")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading products from database: " + e.getMessage(), e);
        }
        return products;
    }
    @Override
    public List<Product> getProductByUserId(Long userId) {
        String sql = "SELECT * FROM products WHERE user_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("account_number"),
                            resultSet.getBigDecimal("balance"),
                            resultSet.getString("product_type")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading products from database: " + e.getMessage(), e);
        }
        return products;
    }



    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET user_id = ?, account_number = ?, balance = ?, product_type = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, product.getUserId());
            statement.setString(2, product.getAccountNumber());
            statement.setBigDecimal(3, product.getBalance());
            statement.setString(4, product.getProductType());
            statement.setLong(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }
}
