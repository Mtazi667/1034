package com.example.ui;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DonneUsers {
    private static ObservableList<User> users;
    private static User currentUser;
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static ObservableList<User> getUsersList() {
        if (users.isEmpty()) {
            addFirstUser();
        }
        return users;
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


}
