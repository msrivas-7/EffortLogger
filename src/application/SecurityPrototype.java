package application;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class SecurityPrototype {

    public static final String SECRET_KEY = "1234567891234567";

    public Node getFeatureContent() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label usernameLabel = new Label("Username");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        Button clearButton = new Button("Clear");
        gridPane.add(loginButton, 0, 2);
        gridPane.add(clearButton, 1, 2);

        loginButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            String encryptedUsername = encrypt(username, SECRET_KEY);
            String encryptedPassword = encrypt(password, SECRET_KEY);

            System.out.println("Encrypted username: " + encryptedUsername);
            System.out.println("Encrypted password: " + encryptedPassword);

            String decryptedUsername = decrypt(encryptedUsername, SECRET_KEY);
            String decryptedPassword = decrypt(encryptedPassword, SECRET_KEY);

            System.out.println("Decrypted username: " + decryptedUsername);
            System.out.println("Decrypted password: " + decryptedPassword);
        });
        clearButton.setOnAction(event -> {
            usernameTextField.clear();
            passwordField.clear();
        });

        return gridPane;
    }

    private static String encrypt(String input, String secretKey) {
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
