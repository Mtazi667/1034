package com.example.ui;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PatientsController {
    private static ObservableList<Patient> patients = FXCollections.observableArrayList();
    public static ObservableList<Patient> getPatientsList() {
        return patients;
    }


    public static ObservableList<Patient> getPatients(User user) {
        if (patients.isEmpty()) {
            addFirstPatient();
        }
        System.out.println("patients len: " + patients.size());

        if (user.getRole().equals("Assistant")) {
            return null;
        }
        String parentId = user.getId();
        ObservableList<Patient> result = FXCollections.observableArrayList();

        for (Patient patient : patients) {
            System.out.println("**patient**: " + patient.getFullName()+ " " + patient.getId() + " " + patient.getParentId());
            if (patient.getParentId().equals(parentId)) {
                result.add(patient);
            }
        }
        return result;
    }
    public static void deletePatient(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                patients.remove(patient);
                return;
            }
        }
    }
    public static void addFirstPatient() {
        StringBuilder id = new StringBuilder();
        id.append(0);
        for (int i = 0; i < 15; i++) {
            id.append((int) (Math.random() * 10));
        }
        ArrayList<String> tests = new ArrayList<>();
        tests.add("Test-A");
        tests.add("Test-B");
        String parentId = UserController.getFirstUserId();
        Patient newPatient = new Patient(parentId, id.toString(), "Patient A", "Fille", tests, 18);
        patients.add(newPatient);
    }
    public static void addPatient(User user, String fullName, String gender, ArrayList<String> tests, int age){
        //generate unique random id
        //generate random string of exactly 16 characters of numbers
        String role = user.getRole();
        if (!role.equals("Assistant") && !role.equals("Doctor") && !role.equals("Admin")){
            return;
        }
        StringBuilder id = new StringBuilder();
        id.append(patients.size());
        int size = !patients.isEmpty() ? 16- (int)(Math.log10(patients.size())+1) : 16;
        for (int i = 0; i < size; i++) {
            id.append((int) (Math.random() * 10));
        }
        Patient newPatient = new Patient(user.getParentId(), id.toString(), fullName, gender, tests, age);
        patients.add(newPatient);
    }
    public static void updatePatient(String id, String fullName, String gender , int age, ArrayList<String> tests, String progress){
        for (Patient patient : patients){
            if (patient.getId().equals(id)){
                patient.setFullName(fullName);
                patient.setGender(gender);
                patient.setAge(age);
                patient.setTests(tests);
                patient.setProgress(progress);
                PatientsWindowController.setUpdate(false);
                // we add a patient then we remove it to trigger the update
                PatientsController.addPatient(UserController.getCurrentUser(),"", "", new ArrayList<>(), 0);
                String lastId = patients.get(patients.size()-1).getId();
                deletePatient(lastId);
                return;
    }  }
    }
    public static void deleteTest(Patient patient, String test){
        patient.removeTest(test);
    }
    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
    public Patient getPatient(String id, User user) {
        String parentId = Objects.equals(user.getRole(), "Doctor") ? user.getId() : user.getParentId();
        if (Objects.equals(user.getRole(), "Assistant")) {
            return null;
        }
        for (Patient patient : patients) {
            if (patient.getId().equals(id) && (patient.getParentId().equals(parentId))) {
                return patient;
            }
        }
        return null;
    }
}
