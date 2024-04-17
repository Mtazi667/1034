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

public class AddPatientWindowController {
    private User user;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField gender;
    @FXML
    private CheckBox testA;
    @FXML
    private CheckBox testB;
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
        User user = PatientsWindowController.getCurrentUser();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String gender = this.gender.getText();
        //we calcuate the age in years
        int age = java.time.LocalDate.now().getYear() - this.birthDate.getValue().getYear();
        //we create a list of tests
        ArrayList<String> tests = new ArrayList<>();
        if (testA.isSelected()){
            tests.add("Test-A");
        }
        if (testB.isSelected()){
            tests.add("Test-B");
        }
        if (!PatientsWindowController.isUpdate())
        {
            PatientsController.addPatient(user, firstName + " " + lastName,
                    gender, tests, age);
        }
        else{
            Patient patient = PatientsWindowController.getSelectedPatient();
            PatientsController.updatePatient(patient.getId(), firstName + " " + lastName, gender ,age, tests, (String) progress.getValue());
        }
        close((Node) event.getSource());
    }
    public void initialize() {
        System.out.println("AddPatientWindowController initialized");
        if (PatientsWindowController.isUpdate()){
            Patient patient = PatientsWindowController.getSelectedPatient();
            String[] fullName = patient.getFullName().split(" ");
            this.firstName.setText(fullName[0]);
            this.lastName.setText(fullName[1]);
            this.gender.setText(patient.getGender());
            this.birthDate.setValue(java.time.LocalDate.now().minusYears(patient.getAge()));
            for (String test : patient.getTestsArray()){
                if (test.equals("Test-A")){
                    testA.setSelected(true);
                }
                if (test.equals("Test-B")){
                    testB.setSelected(true);
                }
            }
            this.progress.setValue(patient.getProgress());
        }
    }
    private void close(Node element){
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }

}
