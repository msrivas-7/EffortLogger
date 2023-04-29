package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EffortLogger extends Application {

    // create buttons for each prototype
    private Button feature1Button = new Button("Security Prototype");
    private Button feature2Button = new Button("User Acceptance Prototype");
    private Button feature3Button = new Button("Post Deployment Prototype");

    // add a header text
    private Text header = new Text("Effort Logger");

    private VBox buttonLayout = new VBox(20, header, feature1Button, feature2Button, feature3Button);
    private BorderPane mainLayout = new BorderPane();
    private MenuBar menuBar = new MenuBar();

    @Override
    public void start(Stage stage) {
        // Create the menu items
        MenuItem backToMain = new MenuItem("Back to Main");
        MenuItem securityPrototype = new MenuItem("Security Prototype");
        MenuItem userAcceptancePrototype = new MenuItem("User Acceptance Prototype");
        MenuItem postDeploymentPrototype = new MenuItem("Post Deployment Prototype");

        // Set the onAction event handlers for the menu items
        backToMain.setOnAction(e -> {
            mainLayout.setCenter(buttonLayout);
            menuBar.setVisible(false);
        });

        securityPrototype.setOnAction(e -> {
            SecurityPrototype feature1 = new SecurityPrototype();
            mainLayout.setCenter(feature1.getFeatureContent());
            menuBar.setVisible(true);
        });

        userAcceptancePrototype.setOnAction(e -> {
            UserAcceptancePrototype feature2 = new UserAcceptancePrototype();
            mainLayout.setCenter(feature2.getFeatureContent());
            menuBar.setVisible(true);
        });

        postDeploymentPrototype.setOnAction(e -> {
            PostDeploymentPrototype feature3 = new PostDeploymentPrototype();
            mainLayout.setCenter(feature3.getFeatureContent());
            menuBar.setVisible(true);
        });

        // Add the menu items to the menu
        Menu menu = new Menu("Menu");
        menu.getItems().addAll(backToMain, securityPrototype, userAcceptancePrototype, postDeploymentPrototype);

        // Create the menu bar and add the menu to it
        menuBar = new MenuBar(menu);
        menuBar.setVisible(false);

        // Set the onAction event handler for the feature buttons
        feature1Button.setOnAction(e -> {
            SecurityPrototype feature1 = new SecurityPrototype();
            mainLayout.setCenter(feature1.getFeatureContent());
            menuBar.setVisible(true);
        });

        feature2Button.setOnAction(e -> {
            UserAcceptancePrototype feature2 = new UserAcceptancePrototype();
            mainLayout.setCenter(feature2.getFeatureContent());
            menuBar.setVisible(true);
        });

        feature3Button.setOnAction(e -> {
            PostDeploymentPrototype feature3 = new PostDeploymentPrototype();
            mainLayout.setCenter(feature3.getFeatureContent());
            menuBar.setVisible(true);
        });

        // Set the button styles and hover effect
        String buttonStyle = "-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-padding: 10 20;";
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
        buttonLayout = new VBox(20, header, feature1Button, feature2Button, feature3Button);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 20;");

        // Set the header style
        header.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        header.setStyle("-fx-fill: #1e90ff;");

        // Create the main layout and add the components to it
        mainLayout = new BorderPane();
        mainLayout.setTop(menuBar);
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



