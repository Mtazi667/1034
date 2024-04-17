package com.example.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PatientsWindowController {
    @FXML
    private MenuBar topMenu;
    @FXML
    private Label Success;
    @FXML
    private TableView<Patient> PatientsTable;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> genderColumn;
    @FXML
    private TableColumn<User, String> statusColumn;
    @FXML
    private TableColumn<User, String> testsColumn;
    @FXML
    private ChoiceBox testChoiceBox;
    @FXML
    private Label success;
    private ObservableList<Patient> patients;

    private StringProperty selectedPatientProperty = new SimpleStringProperty();
    private static User currentUser;
    private static Patient selectedPatient;
    private static boolean update = false;
    public static User getCurrentUser(){
        return currentUser;
    }
    public static boolean isUpdate(){
        return update;
    }
    public static void setUpdate(boolean update){
        PatientsWindowController.update = update;
    }
    public static Patient getSelectedPatient(){
        return selectedPatient;
    }
    @FXML
    private void onUpdateClick(){
        if (selectedPatient == null){
            success.setText("Please select a patient to update");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                success.setText("");
                            });
                        }
                    },
                    2000
            );
        }
        try {
            setUpdate(true);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("add-patient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
            stage.setTitle("Update Patient");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
            alert.getButtonTypes().add(ButtonType.OK);
        }
    }
    @FXML
    private void onDeleteTestClick(){
        if (selectedPatient == null){
            return;
        }
        String test = (String) testChoiceBox.getValue();
        System.out.println("Test: " + test);
        if (test == null){
            //
            success.setText("Please select a test to delete");
            //set text back to empty after 2 seconds
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                success.setText("");
                            });
                        }
                    },
                    2000
            );
            return;
        }
        PatientsController.deleteTest(selectedPatient, test);
        testChoiceBox.getItems().clear();
        for (String tst : selectedPatient.getTestsArray()){
            testChoiceBox.getItems().add(tst);
        }
        PatientsTable.refresh();
    }
    public void initialize(){
        currentUser = UserController.getCurrentUser();
        Menu utilisateurs = new Menu("Users");

        MenuItem afficherUtilisateurs = new MenuItem("Acces Users");
        MenuItem addUtilisateur = new MenuItem("Add User");
        if (!currentUser.getRole().equals("Admin")){
            afficherUtilisateurs.setDisable(true);
            addUtilisateur.setDisable(true);
        }
        utilisateurs.getItems().addAll(addUtilisateur, afficherUtilisateurs);
        Menu Patients = new Menu("Patients");
        MenuItem afficherPatients = new MenuItem("Acces Patients");
        MenuItem addPatient = new MenuItem("Add Patient");
        addPatient.setOnAction(event -> {
            System.out.println("Add Patient");
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("add-patient.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 320, 250);
                stage.setTitle("Ajouter Patient");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setAlwaysOnTop(true);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
                alert.getButtonTypes().add(ButtonType.OK);
            }
        });
        if (currentUser.getRole().equals("Examiner")){
            addPatient.isDisable();
        }
        Menu Examiners = new Menu("Examiners");
        MenuItem afficherExaminers = new MenuItem("Acces Examiners");
        MenuItem addExaminer = new MenuItem("Add Examiner");
        Examiners.getItems().addAll(addExaminer, afficherExaminers);
        if (!currentUser.getRole().equals("Admin") && !currentUser.getRole().equals("Doctor")){
            Examiners.isDisable();
        }
        afficherPatients.setDisable(true);
        Menu Setting = new Menu("Settings");
        MenuItem ChangePassword = new MenuItem("Change PIN");
        MenuItem Logout = new MenuItem("Logout");

        Menu Help = new Menu("Help");
        Logout.setOnAction(event -> {
            System.out.println("Logout");
            try {
                Stage currentStage = (Stage) success.getScene().getWindow();
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("Auth.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 400, 370);
                stage.setTitle("Connexion");
                stage.setScene(scene);
                stage.show();
                currentStage.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
                alert.getButtonTypes().add(ButtonType.OK);
            }


        });
        Setting.getItems().addAll(ChangePassword, Logout);
        Patients.getItems().addAll(addPatient, afficherPatients);
        topMenu.getMenus().addAll(utilisateurs, Patients, Examiners, Setting, Help);
        System.out.println("User: " + currentUser.getFullName());
        patients = PatientsController.getPatients(currentUser);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.prefWidthProperty().bind(PatientsTable.widthProperty().multiply(0.2));
        idColumn.setStyle("-fx-alignment: CENTER;");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.prefWidthProperty().bind(PatientsTable.widthProperty().multiply(0.2));
        fullNameColumn.setStyle("-fx-alignment: CENTER;");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.prefWidthProperty().bind(PatientsTable.widthProperty().multiply(0.17));
        genderColumn.setStyle("-fx-alignment: CENTER;");
        testsColumn.setCellValueFactory(new PropertyValueFactory<>("tests"));
        testsColumn.prefWidthProperty().bind(PatientsTable.widthProperty().multiply(0.23));
        testsColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        statusColumn.prefWidthProperty().bind(PatientsTable.widthProperty().multiply(0.18));
        statusColumn.setStyle("-fx-alignment: CENTER;");
        PatientsTable.setItems(PatientsController.getPatientsList());
        PatientsTable.refresh();
        PatientsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedPatient = newValue;
            selectedPatientProperty.set(newValue.getId());
            testChoiceBox.getItems().clear();
            for (String test : newValue.getTestsArray()){
                testChoiceBox.getItems().add(test);
            }
            PatientsTable.refresh();
        });
    }
}
