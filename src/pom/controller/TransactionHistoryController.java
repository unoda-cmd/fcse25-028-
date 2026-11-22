
package pom.controller;

import pom.model.Account;
import pom.model.Customer;
import pom.model.Transaction;
import pom.dao.AccountDAO;
import pom.dao.TransactionDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionHistoryController {

    private Customer customer;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public TransactionHistoryController(Customer customer) {
        this.customer = customer;
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public ArrayList<Transaction> getTransactions() {

        ArrayList<Transaction> allTransactions =
                new ArrayList<>();

        try {
            // Retrieve customer's accounts from the database
            ArrayList<Account> accounts =
                    accountDAO.getAccountsByCustomer(
                            customer.getCustomerid()
                    );

            // Retrieve transactions for each account
            for (Account account : accounts) {

                allTransactions.addAll(
                        transactionDAO.getTransactions(
                                account.getAccountNumber()
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allTransactions;
    }
}
