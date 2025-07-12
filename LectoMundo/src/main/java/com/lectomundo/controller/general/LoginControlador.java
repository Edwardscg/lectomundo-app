package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.controller.cliente.ClienteControlador;
import com.lectomundo.logic.CorreoService;
import com.lectomundo.logic.UsuarioService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador de la ventana de inicio de sesión.
 * Gestiona la autenticación del usuario y redirige a la vista correspondiente según su tipo.
 */
public class LoginControlador {

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Hyperlink hyperlinkrecuperarContraseña;

    private final UsuarioService usuarioService = new UsuarioService();

    /**
     * Acción asociada al botón de "Iniciar Sesión".
     * Valida las entradas, autentica al usuario y carga la vista correspondiente.
     */
    @FXML
    private void IniciarSesion() {

        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();

        if (correo.isBlank() || contraseña.isBlank()) {

            UIHelper.mostrarAlerta("Entradas vacías", "Llene todos los campos.");
            return;
        }

        try {

            String codigo = CorreoService.generarCodigoDeVerificacion();
            CorreoService.enviarCodigoPorCorreo(correo, codigo);
            boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);

            if(!verificado) return;

            Usuario usuario = usuarioService.loguearUsuario(correo, contraseña);

            Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();

            if (usuario instanceof Cliente cliente) {

                ClienteControlador.cliente = cliente;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/cliente.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                stage.setOnCloseRequest(event -> {

                    event.consume();
                });

                ventana_actual.close();

            } else {

                UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/admin/admin.fxml", "Administrador");
            }

        } catch (IllegalArgumentException e) {

            UIHelper.mostrarAlerta("Error", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error al iniciar sesión.");
        }
    }

    @FXML
    private void AbrirRegistro() {

        Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
        UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/registro.fxml", "Registro");
    }

    @FXML
    private void RecuperarContraseña() {

        Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
        UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/recuperarContraseña.fxml", "Recuperación de Contraseña");
    }
}
