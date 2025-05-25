package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.util.ConfirmDialog;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AccountManagementController {

    private Account currentAccount;

    private AccountService accountService = new AccountService();

    public void initData(Account cuenta) {
        this.currentAccount = cuenta;
        loadAccountData();
    }

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Button btnGoToDeposit;

    @FXML
    private Button btnGoToTransference;

    @FXML
    private Button btnGoToWithdraw;

    @FXML
    private Label lblAccountId;

    @FXML
    private Label lblAccountType;

    @FXML
    private Label lblSaldo;

    @FXML
    void btnBackClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnDeleteAccountClicked(ActionEvent event) {
        boolean confirmed = ConfirmDialog.show("¿Está seguro que desea eliminar esta cuenta?");
        if (confirmed) {
            try {
                boolean success = accountService.deleteAccount(currentAccount);
                if (success) {
                    Session.getCurrentUser().getAccounts().remove(currentAccount.getId());
                    ViewNavigator.changeView("MainDashboard");
                } else {
                    showErrorAlert("No se pudo eliminar la cuenta. Intente nuevamente más tarde.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Ocurrió un error inesperado al eliminar la cuenta.");
            }
        }
    }

    @FXML
    void btnGoToDepositClicked(ActionEvent event) {
        ViewNavigator.changeViewWithData("DepositDashboard", currentAccount);
    }

    @FXML
    void btnGoToTransferenceClicked(ActionEvent event) {
        ViewNavigator.changeViewWithData("TransferenceDashboard", currentAccount);
    }

    @FXML
    void btnGoToWithdrawClicked(ActionEvent event) {
        ViewNavigator.changeViewWithData("WithdrawDashboard", currentAccount);
    }

    private void loadAccountData() {
        if (currentAccount != null) {
            lblAccountId.setText(currentAccount.getId().toString());
            lblAccountType.setText(currentAccount.getAccountType().toString());
            lblSaldo.setText(String.format("$%.2f", currentAccount.getBalance()));
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}