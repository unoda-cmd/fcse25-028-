
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pom.controller.OpenAccountController;
import pom.model.Customer;

public class OpenAccountView {

    private Customer customer;

    public OpenAccountView(Customer customer) {
        this.customer = customer;
    }

    public VBox getView() {

        OpenAccountController controller =
                new OpenAccountController(customer);

        Label title = new Label("OPEN NEW ACCOUNT");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label accountLabel = new Label("Select Account Type:");

        ComboBox<String> accountType = new ComboBox<>();
        accountType.getItems().addAll(
                "Savings Account",
                "Investment Account",
                "Cheque Account"
        );

        accountType.setPromptText("Choose account type");

        Label depositLabel = new Label("Initial Deposit:");

        TextField depositField = new TextField();
        depositField.setPromptText("Enter deposit amount (BWP)");

        Label employerLabel = new Label("Employer / Company Name:");

        TextField employerField = new TextField();
        employerField.setPromptText("Enter employer name");

        Label addressLabel = new Label("Employer Address:");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter employer address");

        Button openButton = new Button("Open Account");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {

            DashboardView dashboard = new DashboardView(customer);

            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((Button) event.getSource()).getScene().getWindow();

            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 500, 450
            );

            stage.setTitle("Banking System - Dashboard");
            stage.setScene(scene);
        });

        Label message = new Label();

        openButton.setOnAction(event -> {

            try {
                String type = accountType.getValue();

                double deposit = Double.parseDouble(
                        depositField.getText()
                );

                String result = controller.openAccount(
                        type,
                        deposit,
                        employerField.getText(),
                        addressField.getText()
                );

                message.setText(result);

                if (result.equals("Account opened successfully!")) {
                    message.setStyle("-fx-text-fill: green;");
                    depositField.clear();
                    employerField.clear();
                    addressField.clear();
                    accountType.setValue(null);
                } else {
                    message.setStyle("-fx-text-fill: red;");
                }

            } catch (NumberFormatException e) {
                message.setText("Please enter a valid deposit amount.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        VBox layout = new VBox(12);

        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                accountLabel,
                accountType,
                depositLabel,
                depositField,
                employerLabel,
                employerField,
                addressLabel,
                addressField,
                openButton,
                cancelButton,
                message
        );

        layout.setStyle("-fx-background-color: #f0f4f8;");

        return layout;
    }
}
