package application;

import application.EffortLogger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class SecurityPrototype {

    public static final String SECRET_KEY = "1234567891234567";

    public Node getFeatureContent() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column5 = new ColumnConstraints();
        column3.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        String textFieldStyle = "-fx-pref-width: 300; -fx-font-size: 14;"; // Set preferred width and font size for text fields
        String labelStyle = "-fx-alignment: CENTER_LEFT; -fx-font-size: 18;"; // Set alignment and font size for labels
        String buttonStyle = "-fx-font-size: 14; -fx-base: #1e90ff; -fx-text-fill: white; -fx-pref-width: 150;"; // Set button style

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle(labelStyle);
        TextField usernameTextField = new TextField();
        usernameTextField.setStyle(textFieldStyle);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle(labelStyle);
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle(textFieldStyle);

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameTextField, 1, 0);
        GridPane.setColumnSpan(usernameTextField, 2);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        GridPane.setColumnSpan(passwordField, 2);

        Button loginButton = new Button("Login");
        loginButton.setStyle(buttonStyle);
        Button clearButton = new Button("Clear");
        clearButton.setStyle(buttonStyle);
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle(buttonStyle);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(clearButton, 1, 2);
        gridPane.add(signUpButton, 2, 2);

        loginButton.setOnAction(event -> {
            EffortLogger effortLoggerInstance = EffortLogger.getInstance();
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            String encryptedUsername = encrypt(username, SECRET_KEY);
            String encryptedPassword = encrypt(password, SECRET_KEY);

            String storedPassword = effortLoggerInstance.userAccounts.get(encryptedUsername);

            Alert alert;
            if (storedPassword != null && storedPassword.equals(encryptedPassword)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully logged in!");
                alert.showAndWait();
                effortLoggerInstance.setUsernameButtonText(username);
                effortLoggerInstance.setFeature1Button("Log Out");
                effortLoggerInstance.setSecurityButtonText("Log Out");
                effortLoggerInstance.mainLayout.setCenter(effortLoggerInstance.buttonLayout);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid Username/Password");
                alert.setContentText("The username and/or password you entered is incorrect. Please try again.");
                alert.showAndWait();
            }
        });

        clearButton.setOnAction(event -> {
            usernameTextField.clear();
            passwordField.clear();
        });

        signUpButton.setOnAction(event -> {
            EffortLogger effortLoggerInstance = EffortLogger.getInstance();
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            String encryptedUsername = encrypt(username, SECRET_KEY);

            if (effortLoggerInstance.userAccounts.containsKey(encryptedUsername)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up Error");
                alert.setHeaderText(null);
                alert.setContentText("User already exists!");
                alert.showAndWait();
                return;
            }

            String encryptedPassword = encrypt(password, SECRET_KEY);

            effortLoggerInstance.userAccounts.put(encryptedUsername, encryptedPassword);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully signed up!");
            alert.showAndWait();
        });

        return gridPane;
    }

    public static String encrypt(String input, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            Key key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decrypt(String input, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            Key key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(input);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}