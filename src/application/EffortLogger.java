package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;


public class EffortLogger extends Application {

    // create buttons for each prototype
    private Button feature1Button = new Button("Login");
    private Button feature2Button = new Button("OnBoarding");
    private Button feature3Button = new Button("Project Management Logs");

    // add a header text
    private Text header = new Text("Effort Logger");

    private VBox buttonLayout = new VBox(20, header, feature1Button, feature2Button, feature3Button);
    private BorderPane mainLayout = new BorderPane();
//    private MenuBar menuBar = new MenuBar();

    	@Override
   	public void start(Stage stage) {
 	    //Create the menu items
   	    MenuItem backToMain = new MenuItem("Homepage");
   	    MenuItem securityPrototype = new MenuItem("Login");
   	    MenuItem userAcceptancePrototype = new MenuItem("OnBoarding");
   	    MenuItem postDeploymentPrototype = new MenuItem("Project Management Logs");

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
   	    Button securityPrototypeButton = new Button("Login");
   	    Button userAcceptancePrototypeButton = new Button("OnBoarding");
   	    Button postDeploymentPrototypeButton = new Button("Project Management Logs");

   	    // Set the onAction event handlers for the menu buttons
   	    backToMainButton.setOnAction(backToMain.getOnAction());
   	    securityPrototypeButton.setOnAction(securityPrototype.getOnAction());
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
    	HBox menuButtonBar = new HBox(5, backToMainButton, securityPrototypeButton, userAcceptancePrototypeButton, postDeploymentPrototypeButton);
        menuButtonBar.setStyle(menuBarStyle);
        // Set the onAction event handler for the feature buttons
        feature1Button.setOnAction(e -> {
            SecurityPrototype feature1 = new SecurityPrototype();
            mainLayout.setCenter(feature1.getFeatureContent());
            //menuBar.setVisible(true);
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



