package com.example.ui;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PatientsController {
    private final ObservableList<Patient> patients;

    public PatientsController() {
        patients = FXCollections.observableArrayList();
    }

    public ObservableList<Patient> getPatients(User user) {
        if (user.getRole().equals("Assistant")) {
            return null;
        }
        String parentId = user.getParentId();
        ObservableList<Patient> result = FXCollections.observableArrayList();
        for (Patient patient : patients) {
            if (patient.getParentId().equals(parentId)) {
                result.add(patient);
            }
        }
        return result;
    }
    public  void addPatient(User user, String fullName, String gender, ArrayList<String> tests, int age){
        //generate unique random id
        //generate random string of exactly 16 characters of numbers
        String role = user.getRole();
        if (!role.equals("Assistant") && !role.equals("Doctor")){
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
