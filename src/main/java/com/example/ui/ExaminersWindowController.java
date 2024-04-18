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

public class ExaminersWindowController {

    @FXML
    private MenuBar topMenu;
    @FXML
    private Label Success;
    @FXML
    private TableView<Examiner> ExaminersTable;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> genderColumn;
    @FXML
    private TableColumn<User, String> statusColumn;
    @FXML
    private TableColumn<User, String> niveauxColumn;
    @FXML
    private ChoiceBox niveauChoiceBox;
    @FXML
    private Label success;
    private ObservableList<Examiner> examiners;

    private StringProperty selectedExaminerProperty = new SimpleStringProperty();
    private static User currentUser;
    private static Examiner selectedExaminer;
    private static boolean update = false;
    public static User getCurrentUser(){
        return currentUser;
    }
    public static boolean isUpdate(){
        return update;
    }
    public static void setUpdate(boolean update){
        ExaminersWindowController.update = update;
    }
    public static Examiner getSelectedPatient(){
        return selectedExaminer;
    }
    @FXML
    private void onUpdateClick(){
        if (selectedExaminer == null){
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
            FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("add-examiner.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
            stage.setTitle("Update Examiner");
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
        if (selectedExaminer == null){
            return;
        }
        String niveau = (String) niveauChoiceBox.getValue();
        System.out.println("Niveau: " + niveau);
        if (niveau == null){
            //
            success.setText("Please select a niveau to delete");
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
        ExaminersController.deleteNiveau(selectedExaminer, niveau);
        niveauChoiceBox.getItems().clear();
        //for (String niveau : selectedExaminer.getNiveauxArray()){
          //  niveauChoiceBox.getItems().add(niveau);
        //}
        ExaminersTable.refresh();
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
        //Menu Examiners = new Menu("Examiners");
        //var afficherExaminers = new MenuItem("Acces Examiners");
        MenuItem addExaminer = new MenuItem("Add Examiner");
        addExaminer.setOnAction(event -> {
            System.out.println("Add Examiner");
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Auth.class.getResource("add-examiner.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 320, 250);
                stage.setTitle("Ajouter Examiner");
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
            addExaminer.isDisable();
        }
        Menu Examiners = new Menu("Examiners");
        MenuItem afficherExaminers = new MenuItem("Acces Examiners");
        //MenuItem addExaminer = new MenuItem("Add Examiner");
        Examiners.getItems().addAll(addExaminer, afficherExaminers);
        if (!currentUser.getRole().equals("Admin") && !currentUser.getRole().equals("Doctor")){
            Examiners.isDisable();
        }
        afficherExaminers.setDisable(true);
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
                alert.show();
            }


        });
        Setting.getItems().addAll(ChangePassword, Logout);
        Examiners.getItems().addAll(addExaminer, afficherExaminers);
        topMenu.getMenus().addAll(utilisateurs, Examiners, Examiners, Setting, Help);
        System.out.println("User: " + currentUser.getFullName());
        examiners = ExaminersController.getExaminers(currentUser);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.prefWidthProperty().bind(ExaminersTable.widthProperty().multiply(0.2));
        idColumn.setStyle("-fx-alignment: CENTER;");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.prefWidthProperty().bind(ExaminersTable.widthProperty().multiply(0.2));
        fullNameColumn.setStyle("-fx-alignment: CENTER;");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.prefWidthProperty().bind(ExaminersTable.widthProperty().multiply(0.17));
        genderColumn.setStyle("-fx-alignment: CENTER;");
        niveauxColumn.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        niveauxColumn.prefWidthProperty().bind(ExaminersTable.widthProperty().multiply(0.23));
        niveauxColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        statusColumn.prefWidthProperty().bind(ExaminersTable.widthProperty().multiply(0.18));
        statusColumn.setStyle("-fx-alignment: CENTER;");
        ExaminersTable.setItems(ExaminersController.getExaminersList());
        ExaminersTable.refresh();
        ExaminersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedExaminer = newValue;
            selectedExaminerProperty.set(newValue.getId());
            niveauChoiceBox.getItems().clear();
            for (String niveau : newValue.getNiveauxArray()){
                niveauChoiceBox.getItems().add(niveau);
            }
            ExaminersTable.refresh();
        });
    }
}
