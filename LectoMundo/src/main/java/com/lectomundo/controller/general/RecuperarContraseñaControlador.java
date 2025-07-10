package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.CorreoService;
import com.lectomundo.logic.UsuarioService;
import com.lectomundo.model.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador para la recuperación de contraseñas mediante verificación por correo electrónico.
 */
public class RecuperarContraseñaControlador {

    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private PasswordField txtNuevaContraseña;
    @FXML
    private PasswordField txtConfirmarContraseña;
    @FXML
    private VBox vboxSeccionCodigo;

    private String codigo_generado;

    private final UsuarioService usuarioService = new UsuarioService();

    /**
     * Envía un código de verificación al correo ingresado si el usuario existe.
     */
    @FXML
    private void EnviarCodigo() {

        String correo = txtCorreo.getText().trim();
        if (correo.isBlank()) {

            UIHelper.mostrarAlerta("Error", "Debe ingresar su correo.");
            return;
        }

        Usuario usuario = usuarioService.buscarUsuarioPorCorreo(correo);

        if (usuario == null) {

            UIHelper.mostrarAlerta("Error", "No existe un usuario con el correo ingresado.");
            return;
        }

        codigo_generado = CorreoService.generarCodigoDeVerificacion();
        boolean enviado = CorreoService.enviarCodigoPorCorreo(correo, codigo_generado);

        if (enviado) {

            UIHelper.mostrarAlerta("Éxito", "Se envió el codigo a su correo.");
            vboxSeccionCodigo.setVisible(true);

        } else {

            UIHelper.mostrarAlerta("Error", "No se pudo enviar el código a su correo.");
        }
    }

    /**
     * Verifica el código y cambia la contraseña del usuario si es válido.
     */
    @FXML
    private void ConfirmarCambio() {

        String codigo_ingresado = txtCodigo.getText().trim();
        String nueva_contraseña = txtNuevaContraseña.getText();
        String confirmar_contraseña = txtConfirmarContraseña.getText();

        if (!codigo_ingresado.equals(codigo_generado)) {

            UIHelper.mostrarAlerta("Error", "El código ingresado es incorrecto.");
            return;
        }

        if (nueva_contraseña.isBlank() || confirmar_contraseña.isBlank()) {

            UIHelper.mostrarAlerta("Error", "Los campos de contraseña no pueden estar vacíos.");
            return;
        }

        if (!nueva_contraseña.equals(confirmar_contraseña)) {

            UIHelper.mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }

        boolean usuario_actualizado = usuarioService.actualizarContraseña(txtCorreo.getText(), nueva_contraseña);

        if (usuario_actualizado) {

            UIHelper.mostrarAlerta("Éxito", "La contraseña se cambió correctamente.");
            IrALogin();

        } else {

            UIHelper.mostrarAlerta("Error", "No se pudo actualizar la contraseña.");
        }
    }

    @FXML
    private void IrALogin() {

        Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
        UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/login.fxml", "Login");
    }
}
