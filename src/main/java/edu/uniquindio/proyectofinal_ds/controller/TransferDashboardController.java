package edu.uniquindio.proyectofinal_ds.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import edu.uniquindio.proyectofinal_ds.dao.TransactionDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCTransactionDAO;
import edu.uniquindio.proyectofinal_ds.dto.TransferDTO;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.Transfer;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.ValidationService;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class TransferDashboardController {

    private Account currentAccount;

    private final AccountService accountService = new AccountService();
    private final TransactionDAO transactionDAO = new JDBCTransactionDAO();

    public void initData(Account account) {
        this.currentAccount = account;
        loadAccountsForTransfer();
    }

    @FXML
    private ComboBox<Account> cbAccountToTransfer;

    @FXML
    private TextField tfAmountToTranfer;

    @FXML
    private Label lbMessage;

    @FXML
    private Button btnTransfer;

    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() {
        cbAccountToTransfer.setPromptText("Seleccione cuenta destino");

        cbAccountToTransfer.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Account acc, boolean empty) {
                super.updateItem(acc, empty);
                if (empty || acc == null) {
                    setText(null);
                } else {
                    setText(acc.getAccountType() + " - " + acc.getFormattedBalance());
                }
            }
        });
        cbAccountToTransfer.setButtonCell(cbAccountToTransfer.getCellFactory().call(null));
    }

    private void loadAccountsForTransfer() {
        List<Account> allAccounts = accountService.findAllAccounts();
        
        List<Account> otherAccounts = allAccounts.stream()
            .filter(acc -> !acc.getId().equals(currentAccount.getId()))
            .collect(Collectors.toList());

        cbAccountToTransfer.getItems().setAll(otherAccounts);
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnTransferClicked(ActionEvent event) {
        try {
            Account destination = cbAccountToTransfer.getValue();
            if (destination == null) {
                lbMessage.setText("Debe seleccionar una cuenta destino.");
                return;
            }

            BigDecimal amount = ValidationService.parseAndValidateAmount(tfAmountToTranfer.getText());

            TransferDTO dto = new TransferDTO(currentAccount.getId(), destination.getId(), amount);
            Transfer transfer = new Transfer(dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());

            boolean success = transfer.execute();
            if (success) {
                transactionDAO.saveTransaction(transfer.toRecord());

                currentAccount = accountService.getAccountById(currentAccount.getId());
                destination = accountService.getAccountById(destination.getId());

                lbMessage.setText(String.format(
                    "Transferencia exitosa de $%.2f de %s a %s",
                    amount,
                    currentAccount.getAccountType(),
                    destination.getAccountType()
                ));
                tfAmountToTranfer.clear();
                btnTransfer.setDisable(true);
                btnCancel.setDisable(true);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> ViewNavigator.changeView("MainDashboard"));
                pause.play();
            } else {
                lbMessage.setText("No se pudo realizar la transferencia.");
            }
        } catch (IllegalArgumentException e) {
            lbMessage.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lbMessage.setText("Ocurrió un error al realizar la transferencia.");
        }
    }
}