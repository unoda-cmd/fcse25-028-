
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pom.controller.TransactionHistoryController;
import pom.model.Customer;
import pom.model.Transaction;

public class TransactionHistoryView {

    private Customer customer;

    public TransactionHistoryView(Customer customer) {
        this.customer = customer;
    }

    public VBox getView() {

        TransactionHistoryController controller =
                new TransactionHistoryController(customer);

        Label title = new Label("TRANSACTION HISTORY");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        VBox layout = new VBox(15);

        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(title);

        if (controller.getTransactions().isEmpty()) {

            Label message = new Label("No transactions found.");
            layout.getChildren().add(message);

        } else {

            for (Transaction transaction : controller.getTransactions()) {

                Label details = new Label(
                        "Transaction ID: " + transaction.getTransactionId()
                                + "\nTransaction Type: " + transaction.getTransactionType()
                                + "\nAmount: BWP " + transaction.getAmount()
                                + "\nDate: " + transaction.getDate()
                );

                details.setStyle("-fx-font-size: 14px;");

                layout.getChildren().add(details);
            }
        }

        // Back to Dashboard button
        Button backButton = new Button("Back to Dashboard");

        backButton.setOnAction(event -> {

            DashboardView dashboard = new DashboardView(customer);

            Stage stage = (Stage)
                    ((Button) event.getSource())
                            .getScene().getWindow();

            Scene scene = new Scene(
                    dashboard.getView(), 500, 450
            );

            stage.setTitle("Banking System - Dashboard");
            stage.setScene(scene);
        });

        layout.getChildren().add(backButton);

        layout.setStyle("-fx-background-color: #f0f4f8;");

        // Make transaction history scrollable
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        VBox mainLayout = new VBox(scrollPane);

        return mainLayout;
    }
}
