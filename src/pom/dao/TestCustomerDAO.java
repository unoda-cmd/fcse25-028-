
package pom.dao;

import pom.model.Customer;

public class TestCustomerDAO {

    public static void main(String[] args) {

        CustomerDAO customerDAO = new CustomerDAO();

        Customer customer = new Customer(
                "C999",
                "Tumelo",
                "Chikuba",
                "Gaborone"
        );

        try {
            boolean added = customerDAO.addCustomer(
                    customer,
                    "tumelo999",
                    "TEST_HASH"
            );

            if (added) {
                System.out.println("Test customer created successfully!");
            }

        } catch (Exception e) {
            System.out.println("Test customer creation failed!");
            e.printStackTrace();
        }
    }
}
