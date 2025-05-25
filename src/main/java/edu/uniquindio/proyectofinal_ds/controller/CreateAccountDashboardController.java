package edu.uniquindio.proyectofinal_ds.controller;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.AccountType;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CreateAccountDashboardController {

    private final AccountService accountService = new AccountService();

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private ComboBox<String> cbAccountType;

    @FXML
    private Label lblMessage;

    @FXML
    void initialize() {
        for (AccountType type : AccountType.values()) {
            cbAccountType.getItems().add(type.getDescription());
        }
        cbAccountType.getSelectionModel().selectFirst();
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnCreateAccountClicked(ActionEvent event) {
        String accountTypeDesc = cbAccountType.getValue();
        UUID currentUserId = Session.getCurrentUser().getId();

        try {
            Account newAccount = accountService.createAccount(currentUserId, accountTypeDesc);
            Session.getCurrentUser().addAccount(newAccount);
            lblMessage.setText("Cuenta con id: " + newAccount.getId()  + " creada exitosamente.");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                ViewNavigator.changeView("MainDashboard");
            });
            pause.play();
        } catch (IllegalArgumentException e) {
            lblMessage.setText("Error: " + e.getMessage());
        }
    }
}