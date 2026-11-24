
package pom.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pom.controller.RegistrationController;

public class RegistrationView {

    public void show(Stage stage) {

        Label title = new Label("CUSTOMER REGISTRATION");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField customerId = new TextField();
        customerId.setPromptText("Customer ID");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");

        TextField surname = new TextField();
        surname.setPromptText("Surname");

        TextField address = new TextField();
        address.setPromptText("Address");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        RegistrationController controller =
                new RegistrationController();

        registerButton.setOnAction(event -> {

            boolean successful = controller.register(
                    customerId.getText(),
                    firstName.getText(),
                    surname.getText(),
                    address.getText(),
                    username.getText(),
                    password.getText()
            );

            Alert alert;

            if (successful) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setContentText(
                        "Customer registered successfully!"
                );

                alert.showAndWait();

                new LoginView().start(stage);

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setContentText(
                        "Registration failed. Check your details "
                                + "or whether the customer ID or username "
                                + "already exists."
                );

                alert.showAndWait();
            }
        });

        backButton.setOnAction(event ->
                new LoginView().start(stage)
        );

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                customerId,
                firstName,
                surname,
                address,
                username,
                password,
                registerButton,
                backButton
        );

        layout.setStyle("-fx-background-color: #f0f4f8;");

        stage.setTitle("Customer Registration");
        stage.setScene(new Scene(layout, 450, 600));
        stage.show();
    }
}
