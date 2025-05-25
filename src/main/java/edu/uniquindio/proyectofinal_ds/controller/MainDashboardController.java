package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.util.ConfirmDialog;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class MainDashboardController {

    @FXML
    private Button btnGoToAddAccount;

    @FXML
    private Button btnGoToRecentTransactions;

    @FXML
    private Button btnHamburguer;

    @FXML
    private Hyperlink hlLogout;

    @FXML
    private Label lbWelcome;

    @FXML
    private Label lbInfo;

    @FXML
    private ListView<String> lvAccounts;

    @FXML
    private VBox vbMenu;

    @FXML
    void initialize() {
        lbWelcome.setText("Bienvenido, " + Session.getCurrentUser().getFullName() + "!");
        lbInfo.setText("Rango: " + Session.getCurrentUser().getRank() +
                       " | Puntos: " + Session.getCurrentUser().getPoints());

        lvAccounts.getItems().clear();

        for (Account account : Session.getCurrentUser().getAccounts().values()) {
            lvAccounts.getItems().add(account.getAccountType().toString() + " - Saldo: $" + account.getBalance());
        }
    }

    @FXML
    void btnGoToAddAccountClicked(ActionEvent event) {
        ViewNavigator.changeView("/view/CreateAccountDashboard.fxml");
    }

    @FXML
    void btnGoToRecentTransactionsClicked(ActionEvent event) {
        ViewNavigator.changeView("/view/RecentTransactions.fxml");
    }

    @FXML
    void btnHamburguerClicked(ActionEvent event) {
        vbMenu.setVisible(!vbMenu.isVisible());
        vbMenu.toFront();
    }

    @FXML
    void hlLogoutClicked(ActionEvent event) {
        boolean isLogoutConfirmed = ConfirmDialog.show("¿Está seguro de que desea cerrar sesión?");
        if (isLogoutConfirmed) {
            Session.clearSession();
            ViewNavigator.changeView("/view/login.fxml");
        }
    }
}