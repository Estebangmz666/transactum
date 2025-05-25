package edu.uniquindio.proyectofinal_ds.controller;

import java.math.BigDecimal;

import edu.uniquindio.proyectofinal_ds.dao.AccountDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCAccountDAO;
import edu.uniquindio.proyectofinal_ds.dto.AccountDTO;
import edu.uniquindio.proyectofinal_ds.mapper.AccountMapper;
import edu.uniquindio.proyectofinal_ds.model.AccountType;
import edu.uniquindio.proyectofinal_ds.service.AuthService;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class CreateAccountDashboardController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private ComboBox<String> cbAccountType;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField tfAccountNumber;

    @FXML
    void initialize() {
        for (AccountType type : AccountType.values()) {
            cbAccountType.getItems().add(type.getDescription());
        }
        cbAccountType.getSelectionModel().selectFirst();
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("/view/MainDashboard.fxml");
    }

    @FXML
    void btnCreateAccountClicked(ActionEvent event) {
        String accountNumber = tfAccountNumber.getText();
        String accountTypeDesc = cbAccountType.getValue();

        try {
            AuthService.validateAccountNumber(accountNumber);
            AuthService.validateAccountType(accountTypeDesc);

            AccountType accountType = AccountType.getAccountTypeFromDescription(accountTypeDesc);
            if (accountType == null) {
                lblMessage.setText("Tipo de cuenta inválido.");
                return;
            }

            AccountDTO accountDTO = new AccountDTO(Session.getCurrentUser().getId(), accountType, BigDecimal.ZERO, accountNumber);

            AccountDAO accountDAO = new JDBCAccountDAO();

            accountDAO.saveAccount(AccountMapper.INSTANCE.toAccount(accountDTO));

            lblMessage.setText("Cuenta creada exitosamente.");
            tfAccountNumber.clear();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                ViewNavigator.changeView("/view/MainDashboard.fxml");
            });
            pause.play();
        } catch (IllegalArgumentException e) {
            lblMessage.setText("Error: " + e.getMessage());
        }
    }
}