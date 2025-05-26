package edu.uniquindio.proyectofinal_ds.controller;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.TransactionDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCTransactionDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.TransactionRecord;
import edu.uniquindio.proyectofinal_ds.util.ListUtils;
import edu.uniquindio.proyectofinal_ds.util.Session;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RecentTransactionsController {

    private final TransactionDAO transactionDAO = new JDBCTransactionDAO();

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<TransactionRecord, String> colAmount;

    @FXML
    private TableColumn<TransactionRecord, String> colDate;

    @FXML
    private TableColumn<TransactionRecord, String> colTransactionId;

    @FXML
    private TableColumn<TransactionRecord, String> colType;

    @FXML
    private Label lbMessage;

    @FXML
    private TableView<TransactionRecord> tableView;

    @FXML
    void initialize(){
        colTransactionId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAmount().toString()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimestamp().toString()));
        try{
            loadTransactions();
        } catch (Exception e) {
            lbMessage.setText("Error cargando transacciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnBackClicked(ActionEvent event) {
        ViewNavigator.changeView("MainDashboard");
    }

    private void loadTransactions() throws Exception {
        UUID currentUserId = Session.getCurrentUser().getId();
        List<TransactionRecord> transactions = transactionDAO.getTransactionsByUserId(currentUserId);

        if (transactions.isEmpty()) {
            lbMessage.setText("");
            lbMessage.setText("No hay transacciones recientes.");
        } else {
            java.util.List<TransactionRecord> javaList = ListUtils.toJavaList(transactions);
            tableView.getItems().setAll(javaList);
        }
    }
}