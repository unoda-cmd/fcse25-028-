package pom.controller;

import pom.model.Account;
import pom.model.Customer;
import pom.dao.AccountDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountController {

    private Customer customer;
    private AccountDAO accountDAO;

    public AccountController(Customer customer) {
        this.customer = customer;
        this.accountDAO = new AccountDAO();
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
}