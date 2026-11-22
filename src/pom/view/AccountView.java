
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import pom.controller.AccountController;
import pom.model.Account;
import pom.model.Customer;

public class AccountView {

    private AccountController controller;
    private Customer customer;

    public AccountView(Customer customer) {
        this.customer = customer;
        controller = new AccountController(customer);
    }

    public VBox getView() {

        Label title = new Label("MY BANK ACCOUNTS");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox layout = new VBox(15);

        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(title);

        // Retrieve accounts from the database
        if (controller.getAccounts().isEmpty()) {

            Label noAccounts = new Label(
                    "You have no bank accounts yet."
            );

            layout.getChildren().add(noAccounts);

        } else {

            for (Account account : controller.getAccounts()) {

                Label accountName = new Label(
                        account.getClass().getSimpleName()
                );

                Label balance = new Label(
                        String.format("Balance: BWP %.2f",
                                account.getBalance())
                );

                layout.getChildren().addAll(accountName, balance);
            }
        }

        // Display total balance
        Label totalBalance = new Label(
                String.format("Total Balance: BWP %.2f",
                        controller.getTotalBalance())
        );

        layout.getChildren().add(totalBalance);

        // Back to Dashboard button
        Button backButton = new Button("Back to Dashboard");

        backButton.setOnAction(event -> {

            DashboardView dashboard = new DashboardView(customer);

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();

            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 500, 450
            );

            stage.setTitle("Banking System - Dashboard");
            stage.setScene(scene);
        });

        layout.getChildren().add(backButton);

        layout.setStyle("-fx-background-color: #f0f4f8;");

        return layout;
    }
}
