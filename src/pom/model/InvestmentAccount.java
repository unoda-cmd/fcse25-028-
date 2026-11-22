
package pom.model;

public class InvestmentAccount extends Account
        implements InterestBearing {

    private double interestRate;

    public InvestmentAccount(String accountNumber,
                             double balance,
                             String branch,
                             double interestRate) {

        super(accountNumber, balance, branch);

        if (balance < 500) {
            throw new IllegalArgumentException(
                    "Initial deposit must be at least BWP 500."
            );
        }

        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {

        if (amount > 0) {
            addBalance(amount);

            addTransaction(new Transaction(
                    "T" + System.currentTimeMillis(),
                    "Deposit",
                    amount
            ));

            System.out.println("Deposit successful!");

        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    @Override
    public void withdraw(double amount) {

        if (amount > 0 && amount <= getBalance()) {

            subtractBalance(amount);

            addTransaction(new Transaction(
                    "T" + System.currentTimeMillis(),
                    "Withdrawal",
                    amount
            ));

            System.out.println("Withdrawal successful!");

        } else {
            System.out.println(
                    "Insufficient funds or invalid amount!"
            );
        }
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    @Override
    public void operation() {
        System.out.println("Investment account operation.");
    }

    public double getInterestRate() {
        return interestRate;
    }
}
