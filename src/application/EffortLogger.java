package application;

import application.PostDeploymentPrototype;
import application.SecurityPrototype;
import application.UserAcceptancePrototype;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.HashMap;
import java.util.Map;


public class EffortLogger extends Application {

    // create buttons for each prototype
    private Button feature1Button = new Button("Log In");
    private Button feature2Button = new Button("Manage Team");
    private Button feature3Button = new Button("Project Logs");


    public void setFeature1Button(String input) {
        feature1Button.setText(input);
    }

    public String userButtonStyle = "-fx-text-fill: #333333;";
    public Button usernameButton = new Button("anonymous");
    public Button securityPrototypeButton = new Button("Log In");

    public final Map<String, String> userAccounts = new HashMap<>();
    private static application.EffortLogger instance;

    public static application.EffortLogger getInstance() {
        return instance;
    }

    public void setUsernameButtonText(String newText) {
        usernameButton.setText(newText);
    }

    public void setSecurityButtonText(String newText) {
        securityPrototypeButton.setText(newText);
    }

    public String getUsernameButtonText() {
        return usernameButton.getText();
    }

    // add a header text
    private Text header = new Text("Effort Logger");

    public VBox buttonLayout = new VBox(20, header, feature1Button, feature2Button, feature3Button);
    public BorderPane mainLayout = new BorderPane();

    @Override
    public void start(Stage stage) {
        instance = this;
        //Create the menu items
        MenuItem backToMain = new MenuItem("Homepage");
        MenuItem userAcceptancePrototype = new MenuItem("Manage Team");
        MenuItem postDeploymentPrototype = new MenuItem("Project Logs");
        MenuItem securityPrototype = new MenuItem("Log In");

        // Set the onAction event handlers for the menu items
        backToMain.setOnAction(e -> {
            mainLayout.setCenter(buttonLayout);
//   	        menuBar.setVisible(false);
        });

        securityPrototype.setOnAction(e -> {
            SecurityPrototype feature1 = new SecurityPrototype();
            mainLayout.setCenter(feature1.getFeatureContent());
//            menuBar.setVisible(true);
        });

        userAcceptancePrototype.setOnAction(e -> {
            UserAcceptancePrototype feature2 = new UserAcceptancePrototype();
            mainLayout.setCenter(feature2.getFeatureContent());
//            menuBar.setVisible(true);
        });

        postDeploymentPrototype.setOnAction(e -> {
            PostDeploymentPrototype feature3 = new PostDeploymentPrototype();
            mainLayout.setCenter(feature3.getFeatureContent());
//   	        menuBar.setVisible(true);
        });

        // Create buttons for the menu items
        Button backToMainButton = new Button("Homepage");
        Button userAcceptancePrototypeButton = new Button("Manage Team");
        Button postDeploymentPrototypeButton = new Button("Project Logs");
        usernameButton.setDisable(true);
        usernameButton.setStyle(userButtonStyle);

        // Set the onAction event handlers for the menu buttons
        backToMainButton.setOnAction(backToMain.getOnAction());
        securityPrototypeButton.setOnAction(e -> {
            if (securityPrototypeButton.getText() == "Log Out") {
                setUsernameButtonText("anonymous");
                setSecurityButtonText("Log In");
                setFeature1Button("Log In");
            } else{
                SecurityPrototype feature1 = new SecurityPrototype();
                mainLayout.setCenter(feature1.getFeatureContent());
            }
        });
        userAcceptancePrototypeButton.setOnAction(userAcceptancePrototype.getOnAction());
        postDeploymentPrototypeButton.setOnAction(postDeploymentPrototype.getOnAction());

        // Customize the menu bar style and buttons
        String menuBarStyle = "-fx-background-color: #1e90ff;";
        String menuButtonStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-padding: 5 10;";
        String menuButtonHover = "-fx-background-color: #6495ed;";

//   	    menuBar.setStyle(menuBarStyle);

        backToMainButton.setStyle(menuButtonStyle);
        securityPrototypeButton.setStyle(menuButtonStyle);
        userAcceptancePrototypeButton.setStyle(menuButtonStyle);
        postDeploymentPrototypeButton.setStyle(menuButtonStyle);

        backToMainButton.setOnMouseEntered(e -> backToMainButton.setStyle(menuButtonStyle + menuButtonHover));
        backToMainButton.setOnMouseExited(e -> backToMainButton.setStyle(menuButtonStyle));
        securityPrototypeButton.setOnMouseEntered(e -> securityPrototypeButton.setStyle(menuButtonStyle + menuButtonHover));
        securityPrototypeButton.setOnMouseExited(e -> securityPrototypeButton.setStyle(menuButtonStyle));
        userAcceptancePrototypeButton.setOnMouseEntered(e -> userAcceptancePrototypeButton.setStyle(menuButtonStyle + menuButtonHover));
        userAcceptancePrototypeButton.setOnMouseExited(e -> userAcceptancePrototypeButton.setStyle(menuButtonStyle));
        postDeploymentPrototypeButton.setOnMouseEntered(e -> postDeploymentPrototypeButton.setStyle(menuButtonStyle + menuButtonHover));
        postDeploymentPrototypeButton.setOnMouseExited(e -> postDeploymentPrototypeButton.setStyle(menuButtonStyle));

        // Add the buttons to the menu bar
        HBox leftMenu = new HBox(5, backToMainButton, securityPrototypeButton, userAcceptancePrototypeButton, postDeploymentPrototypeButton);
        HBox rightMenu = new HBox(usernameButton);
        HBox.setHgrow(leftMenu, Priority.ALWAYS);
        HBox.setHgrow(rightMenu, Priority.NEVER);
        HBox menuButtonBar = new HBox(leftMenu, rightMenu);
        menuButtonBar.setStyle(menuBarStyle);
        // Set the onAction event handler for the feature buttons
        feature1Button.setOnAction(e -> {
            if (feature1Button.getText() == "Log Out") {
                setUsernameButtonText("anonymous");
                setFeature1Button("Log In");
                setSecurityButtonText("Log In");
            } else{
                SecurityPrototype feature1 = new SecurityPrototype();
                mainLayout.setCenter(feature1.getFeatureContent());
            }
        });

        feature2Button.setOnAction(e -> {
            UserAcceptancePrototype feature2 = new UserAcceptancePrototype();
            mainLayout.setCenter(feature2.getFeatureContent());
            // menuBar.setVisible(true);
        });

        feature3Button.setOnAction(e -> {
            PostDeploymentPrototype feature3 = new PostDeploymentPrototype();
            mainLayout.setCenter(feature3.getFeatureContent());
            //menuBar.setVisible(true);
        });

        // Set the button styles and hover effect
        String buttonStyle = "-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-padding: 10 20; -fx-pref-width: 200; -fx-pref-height: 40;";
        String buttonHover = "-fx-background-color: #6495ed;";

        feature1Button.setStyle(buttonStyle);
        feature2Button.setStyle(buttonStyle);
        feature3Button.setStyle(buttonStyle);

        feature1Button.setOnMouseEntered(e -> feature1Button.setStyle(buttonStyle + buttonHover));
        feature1Button.setOnMouseExited(e -> feature1Button.setStyle(buttonStyle));

        feature2Button.setOnMouseEntered(e -> feature2Button.setStyle(buttonStyle + buttonHover));
        feature2Button.setOnMouseExited(e -> feature2Button.setStyle(buttonStyle));

        feature3Button.setOnMouseEntered(e -> feature3Button.setStyle(buttonStyle + buttonHover));
        feature3Button.setOnMouseExited(e -> feature3Button.setStyle(buttonStyle));

        // Create the button layout
        buttonLayout = new VBox(30, header, feature1Button, feature2Button, feature3Button);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 20;");

        // Set the header style
        header.setFont(Font.font("Arial", FontWeight.BOLD, 52));
        header.setStyle("-fx-fill: #1e90ff;");

        // Create the main layout and add the components to it
        mainLayout.setTop(menuButtonBar);
//        mainLayout = new BorderPane();
//        mainLayout.setTop(menuBar);
        mainLayout.setCenter(buttonLayout);

        // Create the scene and set it to the stage
        Scene scene = new Scene(mainLayout, 700, 500);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}