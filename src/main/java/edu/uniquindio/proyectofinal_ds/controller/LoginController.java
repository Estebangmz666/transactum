package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCUserDAO;
import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.service.AuthService;
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

    private final UserDAO userDAO = new JDBCUserDAO();

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
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        tfEmail.setStyle("");
        pfPassword.setStyle("");
        lbMessage.setText("");

        try {
            AuthService.validateEmail(email);
            AuthService.validatePassword(password);
        } catch (IllegalArgumentException e) {
            lbMessage.setText(e.getMessage());
            return;
        }
        
        if (!userDAO.userExists(email)) {
            lbMessage.setText("El usuario no existe, por favor regístrate.");
            return;
        }

        if (!userDAO.validateUser(email, password)) {
            lbMessage.setText("Contraseña incorrecta.");
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            lbMessage.setText("Error al obtener el usuario.");
            return;
        }

        Session.setCurrentUser(user);
        
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void hlSignupClicked(ActionEvent event) {
        ViewNavigator.changeView("Signup");
    }
}