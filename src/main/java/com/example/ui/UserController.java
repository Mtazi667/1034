package com.example.ui;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
public class UserController {
    private ObservableList<User> users;

    public UserController() {
        users = FXCollections.observableArrayList();
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
