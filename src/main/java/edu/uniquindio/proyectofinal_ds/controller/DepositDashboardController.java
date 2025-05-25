package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.Deposit;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import java.math.BigDecimal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepositDashboardController {

    private Account currentAccount;

    private final AccountService AccountService = new AccountService();

    public void initData(Account account) {
        this.currentAccount = account;
    }

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDeposit;

    @FXML
    private Label lbMessage;

    @FXML
    private TextField tfAmountToDeposit;

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnDepositClicked(ActionEvent event) {
        String input = tfAmountToDeposit.getText().trim();
        try {
            BigDecimal amount = new BigDecimal(input);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                lbMessage.setText("Ingrese un monto mayor a cero.");
                return;
            }

            Deposit deposito = new Deposit(currentAccount, amount);

            deposito.execute();

            lbMessage.setText("Depósito exitoso. Nuevo saldo: $" + String.format("%.2f", currentAccount.getBalance()));
            tfAmountToDeposit.clear();

        } catch (NumberFormatException e) {
            lbMessage.setText("Ingrese un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
            lbMessage.setText("Ocurrió un error al realizar el depósito.");
        }
    }
}