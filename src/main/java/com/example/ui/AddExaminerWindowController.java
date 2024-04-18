package com.example.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddExaminerWindowController {

    private User user;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField gender;
    @FXML
    private CheckBox niveau1;
    @FXML
    private CheckBox niveau2;
    @FXML
    private DatePicker birthDate;
    @FXML
    private ChoiceBox progress;
    @FXML
    private void onCancelButtonClick(Event event){
        close((Node) event.getSource());
    }

    @FXML
    private void onSaveButtonClick(Event event){
        User user = ExaminersWindowController.getCurrentUser();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String gender = this.gender.getText();

        int age = java.time.LocalDate.now().getYear() - this.birthDate.getValue().getYear();

        ArrayList<String> niveaux = new ArrayList<>();
        if (niveau1.isSelected()){
            niveaux.add("Niveau1");
        }
        if (niveau2.isSelected()){
            niveaux.add("Niveau2");
        }
        if (!ExaminersWindowController.isUpdate())
        {
            ExaminersController.addExaminer(user, firstName, lastName, niveaux, age);
        }
        else{
            Examiner examiner = ExaminersWindowController.getSelectedPatient();
            ExaminersController.updateExaminer(examiner.getId(), firstName + " " + lastName, gender ,age, niveaux, (String) progress.getValue());
        }
        close((Node) event.getSource());
    }
    public void initialize() {
        System.out.println("AddExaminerWindowController initialized");
        if (ExaminersWindowController.isUpdate()){
            Examiner examiner = ExaminersWindowController.getSelectedPatient();
            String[] fullName = examiner.getFullName().split(" ");
            this.firstName.setText(fullName[0]);
            this.lastName.setText(fullName[1]);
            this.gender.setText(examiner.getGender());
            this.birthDate.setValue(java.time.LocalDate.now().minusYears(examiner.getAge()));
            for (String niveau : examiner.getNiveauxArray()){
                if (niveau.equals("Niveau1")){
                    niveau1.setSelected(true);
                }
                if (niveau.equals("Niveau2")){
                    niveau2.setSelected(true);
                }
            }
            this.progress.setValue(examiner.getProgress());
        }
    }
    private void close(Node element){
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }


}
