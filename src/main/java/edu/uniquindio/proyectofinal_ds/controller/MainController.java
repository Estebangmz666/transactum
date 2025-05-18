package edu.uniquindio.proyectofinal_ds.controller;

import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.util.TestDataService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private Button btnLoadUsers;

    @FXML
    private Button btnLogin;

    @FXML
    private ComboBox<String> cbLoadUsers;

    @FXML
    private TableColumn<User, String> colAccumulatedBalance;

    @FXML
    private TableColumn<User, String> colAddress;

    @FXML
    private TableColumn<User, String> colCellphone;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPoints;

    @FXML
    private TableColumn<User, String> colRank;

    @FXML
    private Hyperlink hlAbout;

    @FXML
    private Label lbMessage;

    @FXML
    private TableView<User> tvUsers;

    @FXML
    public void initialize() {
        cbLoadUsers.getItems().addAll(
            "Set Básico",
            "Cuentas Grandes",
            "Prueba de Puntos",
            "Transacciones Cruzadas"
        );
        btnLogin.setDisable(true);
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        colAddress.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
        colCellphone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCellphone()));
        colPoints.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPoints())));
        colRank.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRankDisplayName()));
        colAccumulatedBalance.setCellValueFactory(data -> new SimpleStringProperty("$" + data.getValue().getAccumulatedBalance().toPlainString()));
    }

    @FXML
    void btnLoadUsersClicked(ActionEvent event) {
        String selectedSet = cbLoadUsers.getValue();
        if (selectedSet == null) {
            lbMessage.setText("Por favor, seleccione un set de prueba.");
            return;
        }
        switch (selectedSet) {
            case "Set Básico":
                TestDataService.getBasicUserSet();
                break;
            case "Cuentas Grandes":
                TestDataService.getHugeAccountsSet();
                break;
            case "Prueba de Puntos":
                TestDataService.getUsersWithPointsSet();
                break;
            case "Transacciones Cruzadas":
                TestDataService.getCrossTransactionSet();
                break;
        }
        lbMessage.setText("Set de prueba cargado: " + selectedSet);
        btnLogin.setDisable(false);
    }

    @FXML
    void btnLogin(ActionEvent event) {

    }

    @FXML
    void hlAboutClicked(ActionEvent event) {

    }
}