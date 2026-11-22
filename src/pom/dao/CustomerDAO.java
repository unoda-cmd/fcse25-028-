
package pom.dao;

import pom.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    // CREATE: Register a customer
    public boolean addCustomer(Customer customer,
                               String username,
                               String passwordHash) throws SQLException {

        String sql = "INSERT INTO customers " +
                "(customer_id, first_name, surname, address, username, password_hash) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerid());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getSurname());
            statement.setString(4, customer.getAddress());
            statement.setString(5, username);
            statement.setString(6, passwordHash);

            return statement.executeUpdate() > 0;
        }
    }

    // READ: Find a customer using customer ID
    public Customer getCustomer(String customerId) throws SQLException {

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new Customer(
                            result.getString("customer_id"),
                            result.getString("first_name"),
                            result.getString("surname"),
                            result.getString("address")
                    );
                }
            }
        }

        return null;
    }

    // UPDATE: Update customer information
    public boolean updateCustomer(Customer customer) throws SQLException {

        String sql = "UPDATE customers SET first_name = ?, " +
                "surname = ?, address = ? WHERE customer_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getSurname());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getCustomerid());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE: Delete a customer
    public boolean deleteCustomer(String customerId) throws SQLException {

        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            return statement.executeUpdate() > 0;
        }
    }

    // LOGIN: Find a customer using username and password hash
    public Customer loginCustomer(String username, String passwordHash)
            throws SQLException {

        String sql = "SELECT * FROM customers "
                + "WHERE username = ? AND password_hash = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new Customer(
                            result.getString("customer_id"),
                            result.getString("first_name"),
                            result.getString("surname"),
                            result.getString("address")
                    );
                }
            }
        }

        return null;
    }

}
