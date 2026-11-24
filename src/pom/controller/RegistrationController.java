
package pom.controller;

import pom.dao.CustomerDAO;
import pom.model.Customer;

import java.sql.SQLException;

public class RegistrationController {

    private CustomerDAO customerDAO;

    public RegistrationController() {
        customerDAO = new CustomerDAO();
    }

    public boolean register(String customerId,
                            String firstName,
                            String surname,
                            String address,
                            String username,
                            String password) {

        if (customerId.isBlank() ||
                firstName.isBlank() ||
                surname.isBlank() ||
                address.isBlank() ||
                username.isBlank() ||
                password.isBlank()) {

            return false;
        }

        Customer customer = new Customer(
                customerId,
                firstName,
                surname,
                address
        );

        try {
            return customerDAO.addCustomer(
                    customer,
                    username,
                    password
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

