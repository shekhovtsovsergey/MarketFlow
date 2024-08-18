package ru.shekhovtsov.dao;

import org.springframework.stereotype.Repository;
import ru.shekhovtsov.model.Payment;
import ru.shekhovtsov.model.PaymentStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class PaymentDaoImpl implements PaymentDao {
    private final DataSource dataSource;

    public PaymentDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong("id"));
                payment.setDebitAccount(rs.getString("debit_account"));
                payment.setCreditAccount(rs.getString("credit_account"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setStatus(PaymentStatus.valueOf(rs.getString("status")));
                return Optional.of(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payment by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payments (debit_account, credit_account, amount, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, payment.getDebitAccount());
            statement.setString(2, payment.getCreditAccount());
            statement.setDouble(3, payment.getAmount());
            statement.setString(4, payment.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment", e);
        }
    }

    @Override
    public void updateStatus(Long id, PaymentStatus status) {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.name());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating payment status", e);
        }
    }
}
