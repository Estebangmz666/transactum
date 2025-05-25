package edu.uniquindio.proyectofinal_ds.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties properties = new Properties();

    private static final String PROPERTIES_PATH = "config.properties";

    public static void loadProperties(){
        try (FileInputStream in = new FileInputStream(PROPERTIES_PATH)){
            properties.load(in);
        } catch (IOException e){
            System.err.println("Error al cargar propiedades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getPathFromProperties(String key) {
        String path = properties.getProperty(key);
        if (path == null || path.isEmpty()) {
            System.err.println("La ruta para " + key + " no está definida.");
        }
        if (!new File(PROPERTIES_PATH).exists()) {
            throw new RuntimeException("Archivo de configuración no encontrado: " + PROPERTIES_PATH);
        }
        return path;
    } 
}