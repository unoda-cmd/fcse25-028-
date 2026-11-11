public class ChequeAccount extends Account {

    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance,
                         String branch, String employerName,
                         String employerAddress) {

        super(accountNumber, balance, branch);

        this.employerName = employerName;
        this.employerAddress = employerAddress;
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
            System.out.println("Insufficient funds or invalid amount!");
        }
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
}