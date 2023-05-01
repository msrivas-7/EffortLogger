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
import static application.SecurityPrototype.SECRET_KEY;

public class UserAcceptancePrototype {

    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextField emailField;
    private final TextField usernameField;
    private final ComboBox<String> accessLevelBox;
    private final ListView<application.UserAcceptancePrototype.Member> listView;
    private final VBox listContainer;
    private final BorderPane root;

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
        Label userNameLabel = new Label("Username:");
        userNameLabel.setStyle("-fx-font-size: 14px;");
        usernameField = new TextField();
        usernameField.setPromptText("Enter their username");
        usernameField.setStyle("-fx-font-size: 14px;");
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
        formBox.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, userNameLabel, usernameField, emailLabel, emailField, accessLevelLabel, accessLevelBox, buttonBox);
        root.setCenter(formBox);

        // Member list
        VBox listBox = new VBox(10);
        listBox.setPadding(new Insets(20));
        listBox.setStyle("-fx-background-color: white; -fx-border-color: #BDBDBD; -fx-border-radius: 5px;");
        Label memberLabel = new Label("Members");
        memberLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        listView = new ListView<>();
        listView.setStyle("-fx-font-size: 14px;");
        listView.setCellFactory(new Callback<ListView<application.UserAcceptancePrototype.Member>, ListCell<application.UserAcceptancePrototype.Member>>() {
            @Override
            public ListCell<application.UserAcceptancePrototype.Member> call(ListView<application.UserAcceptancePrototype.Member> param) {
                return new ListCell<application.UserAcceptancePrototype.Member>() {
                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(application.UserAcceptancePrototype.Member item, boolean empty) {
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
                    AlertBox.display("Member Info", "First Name: " + newValue.getFirstName() + "\nLast Name: " + newValue.getLastName() + "\nUsername: " + newValue.getUserName() + "\nEmail: " + newValue.getEmail() + "\nAccess Level: " + newValue.getAccessLevel());
                    listView.getSelectionModel().clearSelection();
                }
            }
        });

        Button confirmDeleteButton = new Button("Delete");
        confirmDeleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        confirmDeleteButton.setOnAction(e -> {
            ObservableList<application.UserAcceptancePrototype.Member> members = listView.getItems();

            // Use removeIf method with a lambda expression to remove checked items
            members.removeIf(application.UserAcceptancePrototype.Member::isDeletable);

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
        String userName = usernameField.getText().trim();
        String email = emailField.getText().trim();
        Object selectedItem = accessLevelBox.getSelectionModel().getSelectedItem();

        EffortLogger effortLoggerInstance = EffortLogger.getInstance();
        String encryptedUsername = SecurityPrototype.encrypt(userName, SECRET_KEY);

        if (!effortLoggerInstance.userAccounts.containsKey(encryptedUsername)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No User Found");
            alert.setHeaderText(null);
            alert.setContentText("No such user exists!");
            alert.showAndWait();
        }
        if (userName.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all required information.");
            alert.showAndWait();
        } else {
            String accessLevel = selectedItem.toString();
            application.UserAcceptancePrototype.Member newMember = new application.UserAcceptancePrototype.Member(firstName, lastName, userName, email, accessLevel);
            for (application.UserAcceptancePrototype.Member currentMember : listView.getItems()) {
                if (currentMember.userName.equals(newMember.userName)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Already Onboarded");
                    alert.setHeaderText(null);
                    alert.setContentText("This user has already been onboarded!");
                    alert.showAndWait();
                    return;
                }
            }
            listView.getItems().add(newMember);
            firstNameField.clear();
            lastNameField.clear();
            usernameField.clear();
            emailField.clear();
            accessLevelBox.getSelectionModel().clearSelection();
        }
    }

    public void saveToCSV() {
        try (FileWriter writer = new FileWriter("OnBoardedMembers.csv")) {
            writer.write("First Name,Last Name,Email,Access Level\n");
            for (application.UserAcceptancePrototype.Member member : listView.getItems()) {
                writer.write(member.getFirstName() + "," + member.getLastName() + "," + member.getUserName() + "," + member.getEmail() + "," + member.getAccessLevel() + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            AlertBox.display("Error", "An error occurred while Exporting the Member Data to CSV.");

            ex.printStackTrace();
        }

        AlertBox.display("Success", "OnBoarded Member Data Exported to CSV successfully.");
    }

    private class Member {
        private final String firstName;
        private final String lastName;
        private final String userName;
        private final String email;
        private final String accessLevel;
        private boolean deletable;

        public Member(String firstName, String lastName, String userName, String email, String accessLevel) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
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

        public String getUserName() {
            return userName;
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
