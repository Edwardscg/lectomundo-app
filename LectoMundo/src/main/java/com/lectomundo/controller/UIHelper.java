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

public class UIHelper {

    public static void mostrarAlerta(String titulo, String mensaje){

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void abrirYCerrarVentanaActual(Stage ventana_actual, String fxml_ubicacion, String titulo){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxml_ubicacion));
            Parent root = loader.load();

            Stage nueva_ventana = new Stage();
            nueva_ventana.setTitle(titulo);
            nueva_ventana.setScene(new Scene(root));
            nueva_ventana.setResizable(false);
            nueva_ventana.show();

            if(ventana_actual!=null){

                ventana_actual.close();
            }

        } catch (Exception e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana." +titulo);
        }
    }

    public static void abrirVentanaOcultandoAnterior(Stage ventana_anterior, String fxml_ubicacion, String titulo){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxml_ubicacion));
            Parent root = loader.load();

            Stage nueva_ventana = new Stage();
            nueva_ventana.setTitle(titulo);
            nueva_ventana.setScene(new Scene(root));
            nueva_ventana.setOnHidden(e -> ventana_anterior.show());
            ventana_anterior.hide();
            nueva_ventana.show();

        }catch (Exception e){

            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cambiar la ventana a :" + titulo);
        }
    }

    public static boolean abrirVentanaDeVerificacion(String correo, String codigo_generado){

        try{

            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource("/view/general/codigoVerificacion.fxml"));
            Parent root = loader.load();

            CodigoVerificacionControlador codigoVerificacionControlador = loader.getController();
            codigoVerificacionControlador.inicializarDatos(correo, codigo_generado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Verificación de Código");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
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
