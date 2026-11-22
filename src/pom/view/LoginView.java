
package pom.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pom.controller.LoginController;
import pom.model.Customer;

public class LoginView extends Application {

    @Override
    public void start(Stage stage) {

        // Title
        Label title = new Label("BANKING SYSTEM");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label subtitle = new Label("Customer Login");

        // Username
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        // Password
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Buttons
        Button loginButton = new Button("Login");
        Button clearButton = new Button("Clear");

        // Create Login Controller
        LoginController controller = new LoginController();

        // Login button
        loginButton.setOnAction(event -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean successful = controller.login(username, password);

            if (successful) {

                Customer customer = controller.getLoggedInCustomer();

                DashboardView dashboard = new DashboardView(customer);

                Scene dashboardScene = new Scene(
                        dashboard.getView(), 500, 450
                );

                stage.setTitle("Banking System - Dashboard");
                stage.setScene(dashboardScene);

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Login Details");
                alert.setContentText(
                        "Please check your username and password."
                );
                alert.showAndWait();
            }
        });

        // Clear button
        clearButton.setOnAction(event -> {
            usernameField.clear();
            passwordField.clear();
        });

        // Layout
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                subtitle,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                clearButton
        );

        layout.setStyle("-fx-background-color: #f0f4f8;");

        // Display window
        Scene scene = new Scene(layout, 400, 450);

        stage.setTitle("Banking System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
