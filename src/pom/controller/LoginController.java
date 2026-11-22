
package pom.controller;

import pom.model.Customer;
import pom.dao.CustomerDAO;

import java.sql.SQLException;

public class LoginController {

    private Customer loggedInCustomer;
    private CustomerDAO customerDAO;

    public LoginController() {
        customerDAO = new CustomerDAO();
    }

    public boolean login(String username, String password) {

        if (username == null || password == null) {
            return false;
        }

        if (username.isBlank() || password.isBlank()) {
            return false;
        }

        try {
            loggedInCustomer = customerDAO.loginCustomer(
                    username,
                    password
            );

            return loggedInCustomer != null;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
