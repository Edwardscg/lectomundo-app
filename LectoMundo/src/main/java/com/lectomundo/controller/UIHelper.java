package com.lectomundo.controller;

import com.lectomundo.controller.general.CodigoVerificacionControlador;
import com.lectomundo.controller.general.ConfirmarAccionControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

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

    public static boolean abrirVentanaDeVerificacion(String correo, String codigo_generado){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource("/view/general/codigoVerificacion.fxml"));
            Parent root = loader.load();

            CodigoVerificacionControlador codigoVerificacionControlador = loader.getController();
            codigoVerificacionControlador.inicializarDatos(correo, codigo_generado);

            Stage stage = new Stage();
            stage.setTitle("Verificación de Código");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return codigoVerificacionControlador.fueVerificado();
        }catch (Exception e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de verificación.");
            return false;
        }
    }

    public static void cerrarVentana(Stage stage){

        if(stage!=null){

            stage.close();
        }
    }

    public static boolean abrirVentanaConfirmacion(String mensaje){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource("/view/general/confirmarAccion.fxml"));
            Parent root = loader.load();

            ConfirmarAccionControlador confirmarAccionControlador = loader.getController();
            confirmarAccionControlador.inicializar(mensaje);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmación");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return ConfirmarAccionControlador.confirmar_accion;

        }catch (IOException e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de confirmación..");
            return false;
        }
    }
}
