
public class Main {

    public static void main(String[] args) {

        // Create a customer
        Customer customer = new Customer(
                "C001",
                "Unoda",
                "Chikuba",
                "Francistown"
        );

        customer.register();

        // Login
        if (customer.login()) {
            System.out.println("Login successful!");

            // Create accounts
            ChequeAccount cheque = new ChequeAccount(
                    "CH001", 1000, "Francistown",
                    "ABC Company", "Francistown"
            );

            InvestmentAccount investment = new InvestmentAccount(
                    "IN001", 500, "Francistown", 5.0
            );

            SavingsAccount savings = new SavingsAccount(
                    "SA001", 2000, "Francistown", 0.05
            );

            // Open accounts
            customer.openAccounts(cheque);
            customer.openAccounts(investment);
            customer.openAccounts(savings);

            // Deposit money
            cheque.deposit(500);
            investment.deposit(200);
            savings.deposit(300);

            // Withdraw money
            cheque.withdraw(200);
            investment.withdraw(100);

            // Calculate interest
            System.out.println("Investment Interest: BWP "
                    + investment.calculateInterest());

            System.out.println("Savings Interest: BWP "
                    + savings.calculateInterest());

            // Display account balances
            System.out.println("\n--- ACCOUNT BALANCES ---");

            for (Account account : customer.getAccounts()) {
                System.out.println(
                        account.getAccountNumber()
                                + " Balance: BWP "
                                + account.getBalance()
                );
            }

        } else {
            System.out.println("Login failed!");
        }
    }
}
