package ru.shekhovtsov.dao;

import org.springframework.stereotype.Repository;
import ru.shekhovtsov.model.Client;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements ClientDao{

    private final DataSource dataSource;

    public ClientDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setEmail(rs.getString("email"));
                client.setPhone(rs.getString("phone"));
                return Optional.of(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO clients (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving client", e);
        }
    }
}
