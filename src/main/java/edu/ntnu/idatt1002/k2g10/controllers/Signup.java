package edu.ntnu.idatt1002.k2g10.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.ntnu.idatt1002.k2g10.Session;
import edu.ntnu.idatt1002.k2g10.Theme;
import edu.ntnu.idatt1002.k2g10.models.User;
import edu.ntnu.idatt1002.k2g10.repositories.UserRepository;
import edu.ntnu.idatt1002.k2g10.utils.crypto.EncryptionException;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Controller for the Signup view.
 *
 * @author chrisoss, trthingnes
 */
public class Signup {
    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField confirmPasswordField;
    @FXML
    private JFXComboBox<String> themePicker;

    /**
     * Runs when the view is loaded.
     */
    @FXML
    public void initialize() {
        // Fill theme picker.
        for (Theme theme : Theme.values()) {
            themePicker.getItems().add(theme.getDisplayName());
            themePicker.getSelectionModel().select(Session.getTheme().getDisplayName());
        }
    }

    /**
     * Attempt to register a new user and log them in.
     */
    @FXML
    public void signup() {
        // Input retrieval
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Input validation
        try {
            if (firstname.isBlank() || lastname.isBlank()) {
                throw new IllegalArgumentException("First or last name was blank.");
            }

            if (!Objects.equals(password, confirmPasswordField.getText())) {
                throw new IllegalArgumentException("Password was not repeated correctly.");
            }

            if (!email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
                throw new IllegalArgumentException("The email provided is not valid.");
            }

            if (!username.matches("([0-9A-Za-zæøåÆØÅ\\-_.])+")) {
                throw new IllegalArgumentException("The username contains invalid characters.");
            }

            String[] usernames = UserRepository.getAllUsernames();
            if (Arrays.asList(usernames).contains(username)) {
                throw new IllegalArgumentException("The username is already taken.");
            }
        } catch (IllegalArgumentException e) {
            Session.dialog("Registration failed", e.getMessage());
            Session.getLogger().info("Could not register user: " + e.getMessage());
            return;
        }

        // Registration process
        User newUser = new User(username, firstname, lastname, email, null);
        try {
            UserRepository.save(newUser, password);
        } catch (IOException | EncryptionException e) {
            Session.dialog("Registration failed", e.getMessage());
            Session.getLogger().severe("Unable to save user to file: " + e.getMessage());
            return;
        }

        // Login process
        Session.setActiveUser(newUser);
        Session.setActivePassword(password);
        try {
            Session.setLocation("upcoming");
        } catch (IOException e) {
            Session.dialog("Registration successful", "Registration succeeded but we're unable to take you to "
                    + "the logged in screen. (" + e.getMessage() + ")");
            Session.getLogger().warning("Unable to view logged in screen.");
        }
    }

    /**
     * Go to the login page.
     * 
     * @throws IOException
     *             If the login page fails to load.
     */
    @FXML
    public void login() throws IOException {
        Session.setLocation("login");
    }

    /**
     * Updates the theme based on the users choice.
     */
    @FXML
    public void changeTheme() {
        Theme newTheme = Theme.get(themePicker.getSelectionModel().getSelectedItem());
        Session.setTheme(newTheme);
        themePicker.getSelectionModel().select(newTheme.getDisplayName());
    }
}