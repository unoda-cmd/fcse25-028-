
package pom.dao;

import pom.model.Account;

import pom.model.SavingsAccount;
import pom.model.InvestmentAccount;
import pom.model.ChequeAccount;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    // CREATE: Save an account
    public boolean addAccount(Account account, String customerId,
                              String accountType, double interestRate,
                              String employerName, String employerAddress)
            throws SQLException {

        String sql = "INSERT INTO accounts " +
                "(account_number, customer_id, account_type, balance, " +
                "branch, interest_rate, employer_name, employer_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccountNumber());
            statement.setString(2, customerId);
            statement.setString(3, accountType);
            statement.setDouble(4, account.getBalance());
            statement.setString(5, account.getBranch());
            statement.setDouble(6, interestRate);
            statement.setString(7, employerName);
            statement.setString(8, employerAddress);

            return statement.executeUpdate() > 0;
        }
    }

    // READ: Find an account
    public double getAccountBalance(String accountNumber)
            throws SQLException {

        String sql = "SELECT balance FROM accounts WHERE account_number = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getDouble("balance");
                }
            }
        }

        return -1;
    }

    // UPDATE: Update account balance
    public boolean updateBalance(String accountNumber, double newBalance)
            throws SQLException {

        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE: Delete an account
    public boolean deleteAccount(String accountNumber)
            throws SQLException {

        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            return statement.executeUpdate() > 0;
        }
    }
    // READ: Get all accounts belonging to a customer
    public ArrayList<Account> getAccountsByCustomer(String customerId)
            throws SQLException {

        ArrayList<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts WHERE customer_id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String accountNumber = result.getString("account_number");
                    String accountType = result.getString("account_type");
                    double balance = result.getDouble("balance");
                    String branch = result.getString("branch");
                    double interestRate = result.getDouble("interest_rate");

                    Account account = null;

                    switch (accountType) {

                        case "Savings Account":
                            account = new SavingsAccount(
                                    accountNumber, balance, branch, interestRate);
                            break;

                        case "Investment Account":
                            account = new InvestmentAccount(
                                    accountNumber, balance, branch, interestRate);
                            break;

                        case "Cheque Account":
                            account = new ChequeAccount(
                                    accountNumber,
                                    balance,
                                    branch,
                                    result.getString("employer_name"),
                                    result.getString("employer_address")
                            );
                            break;
                    }

                    if (account != null) {
                        accounts.add(account);
                    }
                }
            }
        }

        return accounts;
    }
}

