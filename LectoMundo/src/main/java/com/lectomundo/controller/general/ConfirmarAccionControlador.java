package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.Consumer;


public class ConfirmarAccionControlador {

    @FXML private Label lblMensaje;

    public static boolean confirmar_accion = false;

    public void inicializar(String mensaje){

        lblMensaje.setText(mensaje);
    }

    @FXML
    private void accionAceptar(){

        confirmar_accion = true;
        Stage ventana_actual = (Stage) lblMensaje.getScene().getWindow();
        UIHelper.cerrarVentana(ventana_actual);
    }

    @FXML
    private void accionCancelar(){

        confirmar_accion = false;
        Stage ventana_actual = (Stage) lblMensaje.getScene().getWindow();
        UIHelper.cerrarVentana(ventana_actual);
    }
}
