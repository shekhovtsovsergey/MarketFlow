package ru.shekhovtsov.dao;

import org.springframework.stereotype.Repository;
import ru.shekhovtsov.model.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao{

    private final DataSource dataSource;

    public AccountDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<Account> findById(Long id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getString("number"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setStatus(rs.getString("status"));
                return Optional.of(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        String sql = "SELECT * FROM accounts WHERE number = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getString("number"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setStatus(rs.getString("status"));
                return Optional.of(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by number", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (number, balance, status) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getNumber());
            statement.setBigDecimal(2, account.getBalance());
            statement.setString(3, account.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account", e);
        }
    }

    @Override
    public void updateBalance(Long id, BigDecimal newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, newBalance);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account balance", e);
        }
    }

}
