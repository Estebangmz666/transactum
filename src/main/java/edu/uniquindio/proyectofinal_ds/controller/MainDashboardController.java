package edu.uniquindio.proyectofinal_ds.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.util.ConfirmDialog;
import edu.uniquindio.proyectofinal_ds.util.ListUtils;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private ListView<String> lvAccounts;;

    @FXML
    private VBox vbMenu;

    private List<Account> accountList = new ArrayList<>();

    @FXML
    void initialize() {
        lbWelcome.setText("Bienvenido, " + Session.getCurrentUser().getFullName() + "!");
        lbInfo.setText("Rango: " + Session.getCurrentUser().getRank() +
                    " | Puntos: " + Session.getCurrentUser().getPoints());

        accountList.clear();

        edu.uniquindio.proyectofinal_ds.datastructures.List<Account> customList = Session.getCurrentUser().getAccounts().values();

        accountList.addAll(ListUtils.toJavaList(customList));

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Account acc : accountList) {
            items.add(acc.getAccountType() + " - Saldo: $" + acc.getBalance());
        }
        lvAccounts.setItems(items);

        lvAccounts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = lvAccounts.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    Account clickedAccount = accountList.get(selectedIndex);
                    abrirVistaTransacciones(clickedAccount);
                }
            }
        });
    }

    @FXML
    void btnGoToAddAccountClicked(ActionEvent event) {
        ViewNavigator.changeView("CreateAccountDashboard");
    }

    @FXML
    void btnGoToRecentTransactionsClicked(ActionEvent event) {
        ViewNavigator.changeView("RecentTransactions");
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
            ViewNavigator.changeView("login");
        }
    }

    private void abrirVistaTransacciones(Account cuenta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/view/AccountManagement.fxml"));
            Parent root = loader.load();
            AccountManagementController controller = loader.getController();
            controller.initData(cuenta);
            Stage stage = (Stage) lvAccounts.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}