package application;

import java.io.FileWriter;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

public class UserAcceptancePrototype {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private ComboBox<String> accessLevelBox;
    private ListView<Member> listView;
    private VBox listContainer;

    private BorderPane root;

    public UserAcceptancePrototype() {
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Header
        Label headerLabel = new Label("Onboard Members");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        StackPane headerPane = new StackPane(headerLabel);
        headerPane.setAlignment(Pos.CENTER);
        root.setTop(headerPane);

        // Input form
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-border-color: #BDBDBD; -fx-border-radius: 5px;");
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle("-fx-font-size: 14px;");
        firstNameField = new TextField();
        firstNameField.setPromptText("Enter first name");
        firstNameField.setStyle("-fx-font-size: 14px;");
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle("-fx-font-size: 14px;");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter last name");
        lastNameField.setStyle("-fx-font-size: 14px;");
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 14px;");
        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setStyle("-fx-font-size: 14px;");
        Label accessLevelLabel = new Label("Access Level:");
        accessLevelLabel.setStyle("-fx-font-size: 14px;");
        accessLevelBox = new ComboBox<>();
        accessLevelBox.getItems().addAll("TeamMember", "ProjectManager", "Admin");
        accessLevelBox.setPromptText("Select access level");
        accessLevelBox.setStyle("-fx-font-size: 14px;");
        Button onboardButton = new Button("Onboard");
        onboardButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size:14px; -fx-font-weight: bold;");
        onboardButton.setOnAction(e -> onboardMember());
        HBox buttonBox = new HBox(onboardButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        formBox.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, accessLevelLabel, accessLevelBox, buttonBox);
        root.setCenter(formBox);

        // Member list
        VBox listBox = new VBox(10);
        listBox.setPadding(new Insets(20));
        listBox.setStyle("-fx-background-color: white; -fx-border-color: #BDBDBD; -fx-border-radius: 5px;");
        Label memberLabel = new Label("Members");
        memberLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        listView = new ListView<>();
        listView.setStyle("-fx-font-size: 14px;");
        listView.setCellFactory(new Callback<ListView<Member>, ListCell<Member>>() {
            @Override
            public ListCell<Member> call(ListView<Member> param) {
                return new ListCell<Member>() {
                    private CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Member item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setText(item.getFullName());
                            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                                item.setDeletable(isSelected);
                            });
                            checkBox.setSelected(item.isDeletable());
                            setGraphic(checkBox);
                        }
                    }
                };
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Member>() {
            @Override
            public void changed(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
                if (newValue != null) {
                    AlertBox.display("Member Info", "First Name: " + newValue.getFirstName() + "\nLast Name: " + newValue.getLastName() + "\nEmail: " + newValue.getEmail() + "\nAccess Level: " + newValue.getAccessLevel());
                    listView.getSelectionModel().clearSelection();
                }
            }
        });

        Button confirmDeleteButton = new Button("Delete");
        confirmDeleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        confirmDeleteButton.setOnAction(e -> {
            ObservableList<Member> members = listView.getItems();

            // Use removeIf method with a lambda expression to remove checked items
            members.removeIf(Member::isDeletable);

            // Refresh the ListView
            listView.refresh();
        });
        
        Button saveToCSVButton = new Button("Export Member Data");
        saveToCSVButton.setOnAction(e -> saveToCSV());


        HBox buttonBar = new HBox(10, saveToCSVButton, confirmDeleteButton);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        listContainer = new VBox(10, memberLabel, listView, buttonBar);
        listBox.getChildren().add(listContainer);
        root.setRight(listBox);
    }

    public Node getFeatureContent() {
        return root;
    }

    private void onboardMember() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        Object selectedItem = accessLevelBox.getSelectionModel().getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || selectedItem == null) {
            AlertBox.display("Error", "Please fill in all the fields and select an access level.");
        } else {
            String accessLevel = selectedItem.toString();
            Member member = new Member(firstName, lastName, email, accessLevel);
            listView.getItems().add(member);
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            accessLevelBox.getSelectionModel().clearSelection();
        }
    }
    
    public void saveToCSV() {
        try (FileWriter writer = new FileWriter("OnBoardedMembers.csv")) {
            writer.write("First Name,Last Name,Email,Access Level\n");
            for (Member member : listView.getItems()) {
                writer.write(member.getFirstName() + "," + member.getLastName() + "," + member.getEmail() + "," + member.getAccessLevel() + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            AlertBox.display("Error", "An error occurred while Exporting the Member Data to CSV.");

            ex.printStackTrace();
        }
        
        AlertBox.display("Success", "OnBoarded Member Data Exported to CSV successfully.");
    }

    private class Member {
        private String firstName;
        private String lastName;
        private String email;
        private String accessLevel;
        private boolean deletable;

        public Member(String firstName, String lastName, String email, String accessLevel) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.accessLevel = accessLevel;
            this.deletable = false;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getAccessLevel() {
            return accessLevel;
        }

        public boolean isDeletable() {
            return deletable;
        }

        public void setDeletable(boolean deletable) {
            this.deletable = deletable;
        }
    }
}

        		
