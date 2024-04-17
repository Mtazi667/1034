package com.example.ui;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class UserController {
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Patient> patients;
    private static User currentUser;


    public static ObservableList<User> getUsersList() {
        if (users.isEmpty()) {
            addFirstUser();
        }
        return users;
    }
    public static String getFirstUserId() {
        if (!users.isEmpty()) {
            return users.get(0).getId();
        }
        return null;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public ObservableList<User> getUsers(User parent) {
        String parentId = parent.getId();
        ObservableList<User> result = FXCollections.observableArrayList();
        for (User user : users) {
            if (user.getParentId().equals(parentId)) {
                result.add(user);
            }
        }
        return result;
    }
    public User getUserById(String id){
        for (User user : users){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    public static void addFirstUser() {
        StringBuilder id = new StringBuilder();
        id.append(0);
        for (int i = 0; i < 15; i++) {
            id.append((int) (Math.random() * 10));
        }
        User newUser = new User(id.toString(),"Admin Account", "Admin", id.toString());
        users.add(newUser);
        ArrayList<String> tests = new ArrayList<>();
        tests.add("Test-A");
        tests.add("Test-B");

    }
    public void addUser(User user, String fullName, String role) {
        if ((!user.getRole().equals("Admin") && role.equals("Doctor"))
                || (!user.getRole().equals("Doctor") && (role.equals("Assistant") || role.equals("Examiner"))))
                return;
        StringBuilder id = new StringBuilder();
        id.append(users.size());
        int size = !users.isEmpty() ? 16 - (int) (Math.log10(users.size()) + 1) : 16;
        for (int i = 0; i < size; i++) {
            id.append((int) (Math.random() * 10));
        }
        String parentId = user.getRole().equals("Admin") ? id.toString() : user.getParentId();
        User newUser = new User(parentId, id.toString(), fullName, role);
        users.add(newUser);

    }
}
