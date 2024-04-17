package com.example.ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;

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
    private ObservableList<User> users;
    private StringProperty selectedUserProperty = new SimpleStringProperty();
    UserController userController = new UserController();
    @FXML
    protected void onLoginClick(){
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
                Success.setText("Connexion réussie");
            }
            else{
                Success.setText("Pin incorrect");
            }
        }

    }
    @FXML
    protected void onResetClick(){
        PasswordFiled.setText("");
        Success.setText("");
    }
    public void initialize(){
        users = userController.getUsersList();
        idColumn.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        idColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.33));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
        fullNameColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.3));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        roleColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.175));
        statusColumn.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        statusColumn.prefWidthProperty().bind(UsersTable.widthProperty().multiply(0.175));
        UsersTable.getItems().addAll(users);
        System.out.println("User 1"+users.get(0).getFullName());
        System.out.print("Users"+users);
        UsersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUserProperty.set(newSelection.getId());
            }
        });
    }
}