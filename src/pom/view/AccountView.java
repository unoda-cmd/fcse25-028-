
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pom.controller.AccountController;
import pom.model.Account;
import pom.model.Customer;
import pom.model.SavingsAccount;

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

        // Retrieve accounts from database
        for (Account account : controller.getAccounts()) {

            Label accountName = new Label(
                    account.getClass().getSimpleName()
            );

            Label accountNumber = new Label(
                    "Account Number: " + account.getAccountNumber()
            );

            Label balance = new Label(
                    String.format("Balance: BWP %.2f",
                            account.getBalance())
            );

            TextField amountField = new TextField();
            amountField.setPromptText("Enter amount");

            Button depositButton = new Button("Deposit");
            Button withdrawButton = new Button("Withdraw");

            // Deposit button
            depositButton.setOnAction(event -> {

                try {
                    double amount = Double.parseDouble(
                            amountField.getText()
                    );

                    boolean success = controller.deposit(
                            account.getAccountNumber(), amount
                    );

                    if (success) {
                        showMessage(
                                "Deposit successful!",
                                "Your deposit has been saved."
                        );

                        refreshScreen(event);
                    } else {
                        showError(
                                "Deposit failed. Check the amount and try again."
                        );
                    }

                } catch (NumberFormatException e) {
                    showError("Please enter a valid amount.");
                }
            });

            // Withdraw button
            withdrawButton.setOnAction(event -> {

                try {
                    double amount = Double.parseDouble(
                            amountField.getText()
                    );

                    boolean success = controller.withdraw(
                            account.getAccountNumber(), amount
                    );

                    if (success) {
                        showMessage(
                                "Withdrawal successful!",
                                "Your withdrawal has been saved."
                        );

                        refreshScreen(event);
                    } else {
                        showError(
                                "Withdrawal failed. Check the account rules, balance and amount."
                        );
                    }

                } catch (NumberFormatException e) {
                    showError("Please enter a valid amount.");
                }
            });

            // Disable withdrawals for Savings Accounts
            if (account instanceof SavingsAccount) {
                withdrawButton.setDisable(true);
            }

            HBox buttons = new HBox(10);
            buttons.setAlignment(Pos.CENTER);
            buttons.getChildren().addAll(
                    depositButton, withdrawButton
            );

            VBox accountBox = new VBox(8);
            accountBox.setAlignment(Pos.CENTER);
            accountBox.getChildren().addAll(
                    accountName,
                    accountNumber,
                    balance,
                    amountField,
                    buttons
            );

            layout.getChildren().add(accountBox);
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

            Stage stage = (Stage)
                    ((Button) event.getSource())
                            .getScene().getWindow();

            stage.setTitle("Banking System - Dashboard");
            stage.setScene(new Scene(
                    dashboard.getView(), 500, 450
            ));
        });

        layout.getChildren().add(backButton);

        layout.setStyle("-fx-background-color: #f0f4f8;");

        // Make the account screen scrollable
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        VBox mainLayout = new VBox(scrollPane);
        mainLayout.setStyle("-fx-background-color: #f0f4f8;");

        return mainLayout;
    }

    // Refresh account screen
    private void refreshScreen(javafx.event.ActionEvent event) {

        Stage stage = (Stage)
                ((Button) event.getSource())
                        .getScene().getWindow();

        stage.setScene(new Scene(getView(), 500, 600));
    }

    // Success message
    private void showMessage(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Error message
    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transaction Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
