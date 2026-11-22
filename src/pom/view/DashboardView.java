
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pom.controller.DashboardController;
import pom.model.Customer;

public class DashboardView {
    private Customer customer;

    public DashboardView(Customer customer) {
        this.customer = customer;
    }

    public VBox getView() {
        DashboardController controller = new DashboardController();

        Label title = new Label("BANKING SYSTEM");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label welcome = new Label("Welcome to Your Dashboard");

        Button accountsButton = new Button("My Accounts");
        Button openAccountButton = new Button("Open New Account");
        Button transactionsButton = new Button("Transaction History");
        // pom.model.Transaction History button action
        transactionsButton.setOnAction(event -> {
            controller.openTransactionHistory();

            TransactionHistoryView transactionView =
                    new TransactionHistoryView(customer);

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();

            javafx.scene.Scene scene = new javafx.scene.Scene(
                    transactionView.getView(), 500, 450
            );

            stage.setTitle("Banking System -Transaction History");
            stage.setScene(scene);

        });
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(event -> {
            controller.logout();
            LoginView loginView = new LoginView();

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();

            loginView.start(stage);

        });

        // Open New pom.model.Account button action
        openAccountButton.setOnAction(event -> {
            controller.openNewAccount();


            OpenAccountView openAccountView = new OpenAccountView(customer);

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();

            javafx.scene.Scene scene = new javafx.scene.Scene(
                    openAccountView.getView(), 500, 450
            );

            stage.setTitle("Banking System - Open New Account");
            stage.setScene(scene);

        });
        accountsButton.setOnAction(event -> {
            controller.openAccounts();


            AccountView accountView = new AccountView(customer);

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();



            javafx.scene.Scene scene = new javafx.scene.Scene(
                    accountView.getView(), 500, 450
            );

            stage.setTitle("Banking System - My Accounts");
            stage.setScene(scene);

        });

        VBox layout = new VBox(20);

        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                welcome,
                accountsButton,
                openAccountButton,
                transactionsButton,
                logoutButton
        );

        layout.setStyle("-fx-background-color: #f0f4f8;");

        return layout;
    }
}
