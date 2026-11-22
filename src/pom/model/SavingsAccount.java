
package pom.model;

public class SavingsAccount extends Account implements InterestBearing {

    private double interestRate;

    public SavingsAccount(String accountNumber,
                          double balance,
                          String branch,
                          double interestRate) {

        super(accountNumber, balance, branch);
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
        System.out.println(
                "Withdrawals are not allowed on Savings Accounts."
        );
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    @Override
    public void operation() {
        System.out.println("Savings account operation.");
    }

    public double getInterestRate() {
        return interestRate;
    }
}
