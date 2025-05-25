package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCUserDAO;
import edu.uniquindio.proyectofinal_ds.dto.UserDTO;
import edu.uniquindio.proyectofinal_ds.mapper.UserMapper;
import edu.uniquindio.proyectofinal_ds.service.AuthService;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private Button btnSignup;

    @FXML
    private Hyperlink hlGoToLogin;

    @FXML
    private Label lbMessage;

    @FXML
    private TextField tfCellphone;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassword;

    @FXML
    void btnSignupClicked(ActionEvent event) {
        String name = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        String password = tfPassword.getText().trim();
        String confirmPassword = tfConfirmPassword.getText().trim();
        String cellphone = tfCellphone.getText().trim();
        String address = tfAddress.getText().trim();

        try {
            AuthService.validateEmail(email);
            AuthService.validateAddress(address);
            AuthService.validateCellphone(cellphone);
            AuthService.validatePassword(password);

            if (!password.equals(confirmPassword)) {
                lbMessage.setText("Las contraseñas no coinciden.");
                return;
            }

            UserDAO userDAO = new JDBCUserDAO();

            if (userDAO.getUserByEmail(email) != null) {
                lbMessage.setText("El correo electrónico ya está en uso.");
                return;
            }

            UserDTO newUserDTO = new UserDTO();
            newUserDTO.setFullName(name);
            newUserDTO.setEmail(email);
            newUserDTO.setPassword(password);
            newUserDTO.setCellphone(cellphone);
            newUserDTO.setAddress(address);

            userDAO.saveUser(UserMapper.INSTANCE.toUser(newUserDTO));
            Session.setCurrentUser(userDAO.getUserByEmail(email));
            ViewNavigator.changeView("/view/MainDashboard.fxml");

        } catch (IllegalArgumentException e) {
            lbMessage.setText(e.getMessage());
        }
    }
  
    @FXML
    void hlGoToLoginClicked(ActionEvent event) {
        ViewNavigator.changeView("/view/Login.fxml");
    }
}