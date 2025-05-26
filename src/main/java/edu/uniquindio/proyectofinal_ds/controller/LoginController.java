package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.service.UserService;
import edu.uniquindio.proyectofinal_ds.service.ValidationService;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final UserService userService = new UserService();

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink hlSignup;

    @FXML
    private Label lbMessage;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    void btnLoginClicked(ActionEvent event) {
        String email = tfEmail.getText().trim();
        String password = pfPassword.getText();

        try {
            ValidationService.validateEmail(email);
            ValidationService.validatePassword(password);
        } catch (IllegalArgumentException e) {
            lbMessage.setText(e.getMessage());
            return;
        }

        try {
            User user = userService.login(email, password);
            if (user == null) {
                lbMessage.setText("Correo o contraseña incorrectos.");
                return;
            }
            
            Session.setCurrentUser(user);
            System.out.println(user.toString());
            ViewNavigator.changeView("MainDashboard");

        } catch (Exception e) {
            e.printStackTrace();
            lbMessage.setText("Error interno, intenta más tarde.");
        }
    }

    @FXML
    void hlSignupClicked(ActionEvent event) {
        ViewNavigator.changeView("Signup");
    }
}