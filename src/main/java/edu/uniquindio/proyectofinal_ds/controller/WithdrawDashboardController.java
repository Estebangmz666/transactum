package edu.uniquindio.proyectofinal_ds.controller;

import java.math.BigDecimal;

import edu.uniquindio.proyectofinal_ds.dao.TransactionDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCTransactionDAO;
import edu.uniquindio.proyectofinal_ds.dto.WithdrawDTO;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.Withdraw;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.ValidationService;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class WithdrawDashboardController {

    private Account currentAccount;

    public void initData(Account account) {
        this.currentAccount = account;
    }

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnWithdraw;

    @FXML
    private Label lbMessage;

    @FXML
    private TextField tfAmountToWithdraw;

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnWithdrawClicked(ActionEvent event) {
        try {
            BigDecimal amount = ValidationService.parseAndValidateAmount(tfAmountToWithdraw.getText());
            System.out.println("ID de la cuenta actual en el controlador: " + currentAccount.getId());
            
            WithdrawDTO withdrawDTO = new WithdrawDTO(currentAccount.getId(), amount);
            Withdraw withdraw = new Withdraw(withdrawDTO.getAccountId(), withdrawDTO.getAmount());
            
            boolean success = withdraw.execute();

            if (success) {
                TransactionDAO transactionDAO = new JDBCTransactionDAO();
                transactionDAO.saveTransaction(withdraw.toRecord());

                AccountService accountService = new AccountService();

                currentAccount = accountService.getAccountById(currentAccount.getId());

                lbMessage.setText("Retiro exitoso. Nuevo saldo: $" + String.format("%.2f", currentAccount.getBalance()));
                tfAmountToWithdraw.clear();
                
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> ViewNavigator.changeView("MainDashboard"));
                btnWithdraw.setDisable(true);
                btnCancel.setDisable(true);
                pause.play();
            } else {
                lbMessage.setText("No se pudo completar el retiro.");
            }

        } catch (IllegalArgumentException e) {
            lbMessage.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lbMessage.setText("Ocurrió un error al realizar el retiro.");
        }
    }
}