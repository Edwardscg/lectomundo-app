package com.lectomundo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class UIHelper {

    public static void mostrarAlerta(String titulo, String mensaje){

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void abrirVentana(Stage ventana_actual, String fxml_ubicacion, String titulo){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxml_ubicacion));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

            if(ventana_actual!=null){

                ventana_actual.close();
            }

        }catch (IOException e){

            e.printStackTrace();
            System.err.println("Error al cargar: "+ fxml_ubicacion);
        }catch (Exception e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana." +titulo);
        }
    }

    public static void cambiarEscena(Stage stage, String fxml_ubicacion, String titulo){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxml_ubicacion));
            Parent root = loader.load();

            stage.setTitle(titulo);
            stage.setScene(new Scene(root));

        }catch (Exception e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cambiar la escena a :" + titulo);
        }
    }

    public static void cerrarVentana(Stage stage){

        if(stage!=null){

            stage.close();
        }
    }
}
