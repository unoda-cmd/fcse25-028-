import java.util.ArrayList;

public class Customer {
    private String customerid;
    private String firstName;
    private String surname;
    private String address;

    private ArrayList<Account> accounts;

    public Customer(String customerid, String firstName,
                    String surname, String address) {

        this.customerid = customerid;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;

        accounts = new ArrayList<>();
    }
    public void register() {
        System.out.println("Customer registered successfully!");
    }

    public boolean login() {
        return customerid != null && !customerid.isEmpty();
    }

    public void openAccounts(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public String getCustomerid() {
        return customerid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }



}










