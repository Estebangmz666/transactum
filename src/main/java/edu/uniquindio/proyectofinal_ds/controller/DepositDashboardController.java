package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.dao.TransactionDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCTransactionDAO;
import edu.uniquindio.proyectofinal_ds.dto.DepositDTO;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.Deposit;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.ValidationService;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import java.math.BigDecimal;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class DepositDashboardController {

    private Account currentAccount;

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
        try {
            BigDecimal amount = ValidationService.parseAndValidateAmount(tfAmountToDeposit.getText());
            System.out.println("ID de la cuenta actual en el controlador: " + currentAccount.getId());

            DepositDTO depositDTO = new DepositDTO(currentAccount.getId(), amount);
            Deposit deposit = new Deposit(depositDTO.getAccountId(), depositDTO.getAmount());

            boolean success = deposit.execute();

            if (success) {
                TransactionDAO transactionDAO = new JDBCTransactionDAO();
                transactionDAO.saveTransaction(deposit.toRecord());

                AccountService accountService = new AccountService();

                currentAccount = accountService.getAccountById(currentAccount.getId());

                lbMessage.setText("Depósito exitoso. Nuevo saldo: $" + String.format("%.2f", currentAccount.getBalance()));
                tfAmountToDeposit.clear();

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    ViewNavigator.changeView("MainDashboard");
                });
                btnDeposit.setDisable(true);
                btnCancel.setDisable(true);
                pause.play();
            } else {
                lbMessage.setText("No se pudo realizar el depósito.");
            }

        } catch (IllegalArgumentException e) {
            lbMessage.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lbMessage.setText("Ocurrió un error al realizar el depósito.");
        }
    }
}