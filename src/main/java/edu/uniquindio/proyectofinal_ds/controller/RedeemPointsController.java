package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.service.PointsService;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;

public class RedeemPointsController {

    private final PointsService pointsService = new PointsService();

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRedeem;

    @FXML
    private ComboBox<Account> cbAccountToTransfer;

    @FXML
    private Label lbMessage;

    @FXML
    private Label lbPointsAmount;

    @FXML
    private Slider spPointsAmmount;

    private void initializeData() {
        int userPoints = pointsService.getPoints(Session.getCurrentUser().getId());
        lbPointsAmount.setText("Puntos actuales: " + userPoints);

        spPointsAmmount.setMin(100);
        spPointsAmmount.setMax(userPoints > 100 ? userPoints : 100);
        spPointsAmmount.setValue(100);
        spPointsAmmount.setBlockIncrement(100);
        spPointsAmmount.setMajorTickUnit(100);
        spPointsAmmount.setMinorTickCount(0);
        spPointsAmmount.setSnapToTicks(true);
        spPointsAmmount.setShowTickLabels(true);
        spPointsAmmount.setShowTickMarks(true);

        cbAccountToTransfer.getItems().clear();
        var accounts = Session.getCurrentUser().getAccounts().values();
        for (Account account : accounts) {
            cbAccountToTransfer.getItems().add(account);
        }

        if (!cbAccountToTransfer.getItems().isEmpty()) {
            cbAccountToTransfer.setValue(cbAccountToTransfer.getItems().get(0));
        }
    }

    @FXML
    public void initialize() {
        cbAccountToTransfer.setPromptText("Seleccione cuenta destino");

        cbAccountToTransfer.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Account acc, boolean empty) {
                super.updateItem(acc, empty);
                setText(empty || acc == null ? null : acc.getAccountType() + " - " + acc.getFormattedBalance());
            }
        });
        cbAccountToTransfer.setButtonCell(cbAccountToTransfer.getCellFactory().call(null));

        initializeData();
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    @FXML
    void btnRedeemClicked(ActionEvent event) {
        Account selectedAccount = cbAccountToTransfer.getValue();
        int pointsToRedeem = (int) spPointsAmmount.getValue();

        if (selectedAccount == null) {
            lbMessage.setText("Por favor, seleccione una cuenta!");
            return;
        }

        try {
            boolean success = pointsService.redeemPoints(Session.getCurrentUser().getId(), selectedAccount.getId(), pointsToRedeem);
            if (success) {
                lbMessage.setText(String.format("Redención exitosa! Se han depositado $%,d", (pointsToRedeem / 100 * 5000)));
                initializeData();
            } else {
                lbMessage.setText("Asegúrese de tener puntos suficientes para redimir (mínimo 100).");
            }
        } catch (Exception e) {
            lbMessage.setText("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}