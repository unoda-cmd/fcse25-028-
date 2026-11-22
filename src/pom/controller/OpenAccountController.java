
package pom.controller;

import pom.model.*;
import pom.dao.TransactionDAO;

import java.sql.SQLException;

public class OpenAccountController {

    private Customer customer;
    private AccountController accountController;
    private TransactionDAO transactionDAO;

    public OpenAccountController(Customer customer) {
        this.customer = customer;
        this.accountController = new AccountController(customer);
        this.transactionDAO = new TransactionDAO();
    }

    public String openAccount(String accountType,
                              double deposit,
                              String employerName,
                              String employerAddress) {

        if (accountType == null || accountType.isBlank()) {
            return "Please select an account type.";
        }

        if (deposit <= 0) {
            return "Please enter a valid deposit amount.";
        }

        try {
            Account account;
            double interestRate = 0;

            switch (accountType) {

                case "Savings Account":
                    interestRate = 0.05;
                    account = new SavingsAccount(
                            "SAV" + System.currentTimeMillis(),
                            deposit,
                            "Main Branch",
                            interestRate
                    );
                    break;

                case "Investment Account":
                    if (deposit < 500) {
                        return "Investment account requires at least BWP 500.";
                    }

                    interestRate = 5.0;
                    account = new InvestmentAccount(
                            "INV" + System.currentTimeMillis(),
                            deposit,
                            "Main Branch",
                            interestRate
                    );
                    break;

                case "Cheque Account":
                    if (employerName == null || employerName.isBlank()
                            || employerAddress == null
                            || employerAddress.isBlank()) {
                        return "Please enter employer name and address.";
                    }

                    account = new ChequeAccount(
                            "CHQ" + System.currentTimeMillis(),
                            deposit,
                            "Main Branch",
                            employerName,
                            employerAddress
                    );
                    break;

                default:
                    return "Invalid account type.";
            }

            Transaction transaction = new Transaction(
                    "T" + System.currentTimeMillis(),
                    "Initial Deposit",
                    deposit
            );

            // Save account to database
            boolean accountSaved = accountController.addAccount(
                    account,
                    accountType,
                    interestRate,
                    employerName,
                    employerAddress
            );

            if (!accountSaved) {
                return "Could not save account to database.";
            }

            // Save initial deposit transaction to database
            boolean transactionSaved = transactionDAO.addTransaction(
                    transaction,
                    account.getAccountNumber()
            );

            if (!transactionSaved) {
                return "Account saved, but initial transaction could not be saved.";
            }

            return "Account opened successfully!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
