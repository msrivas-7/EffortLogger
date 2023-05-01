package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PostDeploymentPrototype {

    private ListView<String> listView;
    private ListView<String> historyListView;
    private TextField textField;
    private VBox root;
    private String author;
    private LocalDateTime currentTime = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String formattedTime = currentTime.format(formatter);

    public PostDeploymentPrototype() {
        Label activityLabel = new Label("Enter activity:");
        textField = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addItem());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editItem());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteItem());

        Button saveToCSVButton = new Button("Export Project Logs");
        saveToCSVButton.setOnAction(e -> saveToCSV());

        HBox inputBox = new HBox(10, activityLabel, textField, addButton, editButton, deleteButton, saveToCSVButton);
        inputBox.setPadding(new Insets(10));

        listView = new ListView<>();
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    editButton.setDisable(newValue == null);
                    deleteButton.setDisable(newValue == null);
                });

        historyListView = new ListView<>();

        Label historyLabel = new Label("Project Logs");

        root = new VBox(10, inputBox, listView, historyLabel, historyListView);
        root.setPadding(new Insets(10));
    }

    public Node getFeatureContent() {
        return root;
    }

    private void addItem() {
        EffortLogger effortLoggerInstance = EffortLogger.getInstance();
        this.author = effortLoggerInstance.getUsernameButtonText();
        String item = textField.getText().trim();

        if (item.isEmpty()) {
            AlertBox.display("Error", "Activity field can't be empty.");
            return;
        }
        if (author == "anonymous") {
            AlertBox.display("Error", "You must be logged in to log activity!");
            return;
        }
        currentTime = LocalDateTime.now();
        formattedTime = currentTime.format(formatter);

        String listItem = String.format("%s", item);
        listView.getItems().add(listItem);

        String historyItem = String.format("%s added %s at %s", author, item, formattedTime);
        historyListView.getItems().add(historyItem);

        textField.clear();
    }


    private void editItem() {
        EffortLogger effortLoggerInstance = EffortLogger.getInstance();
        this.author = effortLoggerInstance.getUsernameButtonText();
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        String selectedItem = listView.getSelectionModel().getSelectedItem();

        if (author == "anonymous") {
            AlertBox.display("Error", "You must be logged in to log activity!");
            return;
        }

        TextField editTextField = new TextField(selectedItem);

        VBox vbox = new VBox(10, new Label("Enter new value:"), editTextField);
        vbox.setPadding(new Insets(10));

        Stage editStage = new Stage();

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            String newValue = editTextField.getText().trim();

            if (newValue.isEmpty()) {
                AlertBox.display("Error", "The edit field can't be empty.");
                return;
            }

            listView.getItems().set(selectedIndex, newValue);

            currentTime = LocalDateTime.now();
            formattedTime = currentTime.format(formatter);

            String historyItem = String.format("%s edited %s to %s at %s", author, selectedItem, newValue, formattedTime);
            historyListView.getItems().add(historyItem);

            editStage.close();
        });
        vbox.getChildren().add(okButton);

        Scene editScene = new Scene(vbox, 200, 100);
        editStage.setScene(editScene);

        editStage.show();
    }



    private void deleteItem() {
        EffortLogger effortLoggerInstance = EffortLogger.getInstance();
        this.author = effortLoggerInstance.getUsernameButtonText();
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        String selectedItem = listView.getSelectionModel().getSelectedItem();

        if (author == "anonymous") {
            AlertBox.display("Error", "You must be logged in to log activity!");
            return;
        }

        if (selectedItem != null) {
            listView.getItems().remove(selectedIndex);

            currentTime = LocalDateTime.now();
            formattedTime = currentTime.format(formatter);

            String historyItem = String.format(" %s deleted %s at %s", author, selectedItem, formattedTime);
            historyListView.getItems().add(historyItem);
        }
    }

    private void saveToCSV() {
        File outputFile = new File("Project_Logs.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String historyItem : historyListView.getItems()) {
                writer.write(historyItem);
                writer.newLine();
            }
        } catch (IOException e) {
            AlertBox.display("Error", "An error occurred while saving the Project Logs to CSV.");
            e.printStackTrace();
        }

        AlertBox.display("Success", "Project Logs saved to CSV successfully.");
    }


}