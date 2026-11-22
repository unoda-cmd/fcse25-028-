package pom.dao;

import pom.model.SavingsAccount;

public class TestAccountDAO {

    public static void main(String[] args) {

        AccountDAO accountDAO = new AccountDAO();

        try {
            String testAccountNumber =
                    "SAVDEL" + System.currentTimeMillis();

            SavingsAccount account = new SavingsAccount(
                    testAccountNumber,
                    500.00,
                    "Main Branch",
                    0.05
            );

            // Create a temporary account
            boolean added = accountDAO.addAccount(
                    account,
                    "C999",
                    "Savings Account",
                    0.05,
                    null,
                    null
            );

            if (added) {
                System.out.println("Temporary account added successfully!");
            } else {
                System.out.println("Could not add temporary account!");
                return;
            }

            // Delete the temporary account
            boolean deleted =
                    accountDAO.deleteAccount(testAccountNumber);

            if (deleted) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found!");
            }

        } catch (Exception e) {
            System.out.println("Test failed!");
            e.printStackTrace();
        }
    }
}
