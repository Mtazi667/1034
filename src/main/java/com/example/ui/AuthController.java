package com.example.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {
    @FXML
    private Label Success;
    @FXML
    private TableView<User> UsersTable;
    @FXML
    private TextField PasswordFiled;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> statusColumn;
    private static ObservableList<User> users;
    private StringProperty selectedUserProperty = new SimpleStringProperty();
    UserController userController = new UserController();
    @FXML
    protected void onLoginClick(ActionEvent event){
        String pin = PasswordFiled.getText();
        if(pin.length() != 4){
            Success.setText("Le Pin doit contenir 4 chiffres");
        }
        else{
            String selectedUser = selectedUserProperty.get();
            if(selectedUser == null || selectedUser.isEmpty()){
                Success.setText("Merci de selectionner un utilisateur");
                return;
            }
            User user = userController.getUserById(selectedUser);
            if(user == null){
                Success.setText("Utilisateur non trouvé");
            }
            else if(user.getStatus().equals("Suspendu")){
                Success.setText("Compte suspendu");
            }
            else if (user.verifyPIN(pin)){
                try {
                    userController.setCurrentUser(user);
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("patients-window.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 700, 400);
                    stage.setTitle("Patients");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    close((Node) event.getSource());
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
                    alert.getButtonTypes().add(ButtonType.OK);
                }
            }
            else{
                Success.setText("Pin incorrect");
            }
        }

    }
    private void close(Node element) {
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void onResetClick(){
        PasswordFiled.setText("");
        Success.setText("");
    }
    public void initialize(){
        users = UserController.getUsersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        idColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.33));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
        fullNameColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.3));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        roleColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.175));
        statusColumn.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        statusColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.175));
        UsersTable.getItems().addAll(users);
        System.out.println("User 1"+users.get(0).getFullName()+" "+users.get(0).getId());
        System.out.print("Users"+users);
        UsersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUserProperty.set(newSelection.getId());
            }
        });
    }
}