import java.time.LocalDate;

public class Transaction {

    private String transactionId;
    private String transactionType;
    private double amount;
    private String date;

    public Transaction(String transactionId,
                       String transactionType,
                       double amount) {

        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = LocalDate.now().toString();
    }

    public String getDetails() {

        return "Transaction ID: " + transactionId
                + "\nTransaction Type: " + transactionType
                + "\nAmount: BWP " + amount
                + "\nDate: " + date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
