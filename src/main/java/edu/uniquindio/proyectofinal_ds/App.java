package edu.uniquindio.proyectofinal_ds;

import edu.uniquindio.proyectofinal_ds.service.PointsService;
import edu.uniquindio.proyectofinal_ds.util.DatabaseInitializer;
import edu.uniquindio.proyectofinal_ds.util.PropertiesLoader;
import edu.uniquindio.proyectofinal_ds.util.ViewNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    private final static PointsService pointsService = new PointsService(); 

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewNavigator.setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Transactum");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args){
        PropertiesLoader.loadProperties();
        DatabaseInitializer.initializeDatabase();
        pointsService.loadFromDatabase();
        launch(args);
    }
}