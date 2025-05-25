package edu.uniquindio.proyectofinal_ds.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator {

    private static Stage stage;

    public static void setStage(Stage stage) {
        ViewNavigator.stage = stage;
    }

    public static void changeView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}