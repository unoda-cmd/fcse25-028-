
package pom.dao;

import pom.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionDAO {

    // CREATE: Save a transaction
    public boolean addTransaction(Transaction transaction,
                                  String accountNumber)
            throws SQLException {

        String sql = "INSERT INTO transactions " +
                "(transaction_id, account_number, transaction_type, amount) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getTransactionId());
            statement.setString(2, accountNumber);
            statement.setString(3, transaction.getTransactionType());
            statement.setDouble(4, transaction.getAmount());

            return statement.executeUpdate() > 0;
        }
    }

    // READ: Retrieve transactions for an account
    public ArrayList<Transaction> getTransactions(String accountNumber)
            throws SQLException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions " +
                "WHERE account_number = ? ORDER BY transaction_date DESC";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Transaction transaction = new Transaction(
                            result.getString("transaction_id"),
                            result.getString("transaction_type"),
                            result.getDouble("amount")
                    );

                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }

    // UPDATE: Update a transaction
    public boolean updateTransaction(Transaction transaction)
            throws SQLException {

        String sql = "UPDATE transactions SET transaction_type = ?, amount = ? WHERE transaction_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getTransactionType());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getTransactionId());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE: Delete a transaction
    public boolean deleteTransaction(String transactionId)
            throws SQLException {

        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transactionId);

            return statement.executeUpdate() > 0;
        }
    }
}

