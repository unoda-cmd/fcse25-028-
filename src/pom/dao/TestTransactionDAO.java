
package pom.dao;

import pom.model.Transaction;

public class TestTransactionDAO {

    public static void main(String[] args) {

        TransactionDAO transactionDAO = new TransactionDAO();

        try {
            Transaction transaction = new Transaction(
                    "T998",
                    "Deposit",
                    500.00
            );

            boolean added = transactionDAO.addTransaction(
                    transaction,
                    "SAV999"
            );

            if (added) {
                System.out.println("Test transaction added successfully!");
            }

            boolean deleted = transactionDAO.deleteTransaction("T998");

            if (deleted) {
                System.out.println("Transaction deleted successfully!");
            } else {
                System.out.println("Transaction not found!");
            }

        } catch (Exception e) {
            System.out.println("Test failed!");
            e.printStackTrace();
        }
    }
}
