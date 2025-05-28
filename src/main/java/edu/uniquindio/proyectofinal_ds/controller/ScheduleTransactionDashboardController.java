package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.ScheduledTransaction;
import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.ScheduledTransactionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleTransactionDashboardController {

    @FXML private ComboBox<Account> cbDestinationAccount;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Button scheduleButton;
    @FXML private Button cancelButton;
    @FXML private Label statusLabel;

    private final ScheduledTransactionService transactionService = ScheduledTransactionService.getInstance();
    private final AccountService accountService = new AccountService();
    private Account currentAccount;

    @FXML
    public void initialize() {
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        datePicker.setValue(LocalDate.now());
        
        // Configurar el ComboBox de cuentas destino
        cbDestinationAccount.setCellFactory(lv -> new ListCell<>() {
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
        cbDestinationAccount.setButtonCell(cbDestinationAccount.getCellFactory().call(null));
    }

    public void initData(Account account) {
        this.currentAccount = account;
        if (account != null) {
            loadDestinationAccounts();
        }
    }

    private void loadDestinationAccounts() {
        List<Account> allAccounts = accountService.findAllAccounts();
        
        List<Account> otherAccounts = allAccounts.stream()
            .filter(acc -> !acc.getId().equals(currentAccount.getId()))
            .collect(Collectors.toList());

        cbDestinationAccount.getItems().setAll(otherAccounts);
        cbDestinationAccount.setPromptText("Seleccione cuenta destino");
    }

    @FXML
    private void handleSchedule() {
        try {
            Account destinationAccount = cbDestinationAccount.getValue();
            if (destinationAccount == null) {
                statusLabel.setText("❌ Debe seleccionar una cuenta destino.");
                return;
            }
            
            if (amountField.getText().trim().isEmpty() || datePicker.getValue() == null) {
                statusLabel.setText("❌ Todos los campos son obligatorios.");
                return;
            }

            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            LocalDate date = datePicker.getValue();
            int hour = hourSpinner.getValue();
            int minute = minuteSpinner.getValue();

            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            if (dateTime.isBefore(LocalDateTime.now())) {
                statusLabel.setText("❌ La fecha/hora debe ser futura.");
                return;
            }

            ScheduledTransaction transaction = new ScheduledTransaction(currentAccount.getId(), amount, dateTime);
            transactionService.scheduleTransaction(transaction);
            
            transactionService.scheduleTransaction(transaction);
            statusLabel.setText("✅ Transacción programada correctamente para: " + dateTime);
            
        } catch (Exception e) {
            statusLabel.setText("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        returnToAccountManagement();
    }

    private void returnToAccountManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AccountManagement.fxml"));
            Parent root = loader.load();
            
            AccountManagementController controller = loader.getController();
            controller.initData(currentAccount);
            
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error al cargar AccountManagement");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}