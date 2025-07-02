package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.UsuarioService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroControlador {

    UsuarioService usuarioService = new UsuarioService();

    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtContraseña;
    @FXML
    private TextField txtConfirmarContraseña;

    @FXML
    private void Registrarse() {

        String nombre = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contraseña = txtContraseña.getText();
        String confirmar_contraseña = txtConfirmarContraseña.getText();
        String tipo_usuario = "cliente";

        if (nombre.isBlank() || correo.isBlank() || contraseña.isBlank() || confirmar_contraseña.isBlank()) {

            UIHelper.mostrarAlerta("Error", "Todos los datos son obligatorios.");
            return;
        }

        if (!contraseña.equals(confirmar_contraseña)) {

            UIHelper.mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }

        try {

            usuarioService.registrarUsuario(nombre, correo, contraseña, tipo_usuario);

            Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();

            UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/login.fxml", "Login");

        } catch (IllegalArgumentException e) {

            UIHelper.mostrarAlerta("Error", e.getMessage());

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo realizar el registro.");
        }
    }

    @FXML
    private void Cancelar() {

        try {

            Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
            UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/login.fxml", "Login");

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo volver a login.");
        }
    }
}
