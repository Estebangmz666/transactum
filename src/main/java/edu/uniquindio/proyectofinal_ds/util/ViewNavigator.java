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
            FXMLLoader loader = new FXMLLoader(ViewNavigator.class.getResource("/view/" + fxmlPath + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeViewWithData(String fxmlName, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewNavigator.class.getResource("/view/" + fxmlName + ".fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            controller.getClass().getMethod("initData", data.getClass()).invoke(controller, data);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("El controlador no tiene método initData con el tipo correcto");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}