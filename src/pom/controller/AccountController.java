
package pom.controller;

import pom.model.Account;
import pom.model.Customer;
import pom.model.Transaction;
import pom.model.SavingsAccount;
import pom.dao.AccountDAO;
import pom.dao.TransactionDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountController {

    private Customer customer;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public AccountController(Customer customer) {
        this.customer = customer;
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Save account to database
    public boolean addAccount(Account account, String accountType,
                              double interestRate,
                              String employerName, String employerAddress) {
        try {
            boolean saved = accountDAO.addAccount(
                    account,
                    customer.getCustomerid(),
                    accountType,
                    interestRate,
                    employerName,
                    employerAddress
            );

            if (saved) {
                customer.openAccounts(account);
            }

            return saved;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve accounts from database
    public ArrayList<Account> getAccounts() {
        try {
            return accountDAO.getAccountsByCustomer(
                    customer.getCustomerid()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Calculate total balance
    public double getTotalBalance() {
        double total = 0;

        for (Account account : getAccounts()) {
            total += account.getBalance();
        }

        return total;
    }

    // Deposit money
    public boolean deposit(String accountNumber, double amount) {

        if (amount <= 0) {
            return false;
        }

        try {
            for (Account account : getAccounts()) {

                if (account.getAccountNumber().equals(accountNumber)) {

                    account.deposit(amount);

                    Transaction transaction =
                            account.getTransactions().get(
                                    account.getTransactions().size() - 1
                            );

                    boolean balanceUpdated = accountDAO.updateBalance(
                            accountNumber,
                            account.getBalance()
                    );

                    if (!balanceUpdated) {
                        return false;
                    }

                    return transactionDAO.addTransaction(
                            transaction,
                            accountNumber
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Withdraw money
    public boolean withdraw(String accountNumber, double amount) {

        if (amount <= 0) {
            return false;
        }

        try {
            for (Account account : getAccounts()) {

                if (account.getAccountNumber().equals(accountNumber)) {

                    // Savings accounts do not allow withdrawals
                    if (account instanceof SavingsAccount) {
                        return false;
                    }

                    // Prevent withdrawal above available balance
                    if (amount > account.getBalance()) {
                        return false;
                    }

                    account.withdraw(amount);

                    Transaction transaction =
                            account.getTransactions().get(
                                    account.getTransactions().size() - 1
                            );

                    boolean balanceUpdated = accountDAO.updateBalance(
                            accountNumber,
                            account.getBalance()
                    );

                    if (!balanceUpdated) {
                        return false;
                    }

                    return transactionDAO.addTransaction(
                            transaction,
                            accountNumber
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
