import java.util.ArrayList;

public abstract class Account {

    private String accountNumber;
    private double balance;
    private String branch;

    private ArrayList<Transaction> transactions;

    public Account(String accountNumber, double balance,
                   String branch) {

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;

        transactions = new ArrayList<>();
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    protected void addBalance(double amount) {
        balance += amount;
    }

    protected void subtractBalance(double amount) {
        balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }
}