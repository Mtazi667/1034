package com.example.ui;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Objects;

public class ExaminersController {

    private static final ObservableList<Examiner> examiners = FXCollections.observableArrayList();
    public static ObservableList<Examiner> getExaminersList() {
        return examiners;
    }


    public static ObservableList<Examiner> getExaminers(User user) {
        if (examiners.isEmpty()) {
            addFirstExaminer();
        }
        System.out.println("examiners len: " + examiners.size());

        if (user.getRole().equals("Assistant")) {
            return null;
        }
        String parentId = user.getId();
        ObservableList<Examiner> result = FXCollections.observableArrayList();

        for (Examiner examiner : examiners) {
            System.out.println("**examiner**: " + examiner.getFullName()+ " " + examiner.getId() + " " + examiner.getParentId());
            if (examiner.getParentId().equals(parentId)) {
                result.add(examiner);
            }
        }
        return result;
    }
    public static void deleteExaminer(String id) {
        for (Examiner examiner : examiners) {
            if (examiner.getId().equals(id)) {
                examiners.remove(examiner);
                return;
            }
        }
    }
    public static void addFirstExaminer() {
        StringBuilder id = new StringBuilder();
        id.append(0);
        for (int i = 0; i < 15; i++) {
            id.append((int) (Math.random() * 10));
        }
        ArrayList<String> niveaux = new ArrayList<>();
        niveaux.add("Niveau1");
        niveaux.add("Niveau2");
        String parentId = UserController.getFirstUserId();
        Examiner newExaminer = new Examiner(parentId, id.toString(), "Medecin A", "Fille", niveaux, 18);
        examiners.add(newExaminer);
    }
    public static void addExaminer(User user, String fullName, String gender, String s, ArrayList<String> niveaux, int age){

        String role = user.getRole();
        if (!role.equals("Assistant") && !role.equals("Doctor") && !role.equals("Admin")){
            return;
        }
        StringBuilder id = new StringBuilder();
        id.append(examiners.size());
        int size = !examiners.isEmpty() ? 16- (int)(Math.log10(examiners.size())+1) : 16;
        for (int i = 0; i < size; i++) {
            id.append((int) (Math.random() * 10));
        }
        Examiner newExaminer = new Examiner(user.getParentId(), id.toString(), fullName, gender, niveaux, age);
        examiners.add(newExaminer);
    }
    public static void updateExaminer(String id, String fullName, String gender , int age, ArrayList<String> niveaux, String progress){
        for (Examiner examiner : examiners){
            if (examiner.getId().equals(id)){
                examiner.setFullName(fullName);
                examiner.setGender(gender);
                examiner.setAge(age);
                examiner.setNiveaux(niveaux);
                examiner.setProgress(progress);
                ExaminersWindowController.setUpdate(false);

                ExaminersController.addExaminer(UserController.getCurrentUser(),"", "", new ArrayList<>(), 0);
                String lastId = examiners.get(examiners.size()-1).getId();
                deleteExaminer(lastId);
                return;
            }  }
    }
    public static void deleteNiveau(Examiner examiner, String niveau){
        examiner.removeNiveau(niveau);
    }

    public static void addExaminer(User user, String s, String gender, ArrayList<String> niveaux, int age) {
    }

    public void removeExaminer(Examiner examiner) {
        examiners.remove(examiner);
    }
    public Examiner getExaminer(String id, User user) {
        String parentId = Objects.equals(user.getRole(), "Doctor") ? user.getId() : user.getParentId();
        if (Objects.equals(user.getRole(), "Assistant")) {
            return null;
        }
        for (Examiner examiner : examiners) {
            if (examiner.getId().equals(id) && (examiner.getParentId().equals(parentId))) {
                return examiner;
            }
        }
        return null;
    }

}
