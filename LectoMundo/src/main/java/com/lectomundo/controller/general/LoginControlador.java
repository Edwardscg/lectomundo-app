package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.CorreoService;
import com.lectomundo.logic.UsuarioService;
import com.lectomundo.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginControlador {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContraseña;
    @FXML private Hyperlink hyperlinkrecuperarContraseña;

    @FXML
    private void IniciarSesion() throws Exception {

        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();

        if(correo.isEmpty() || contraseña.isEmpty()){

            UIHelper.mostrarAlerta("Entradas vacías", "Llene todos los campos.");
            return;
        }

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.loguearUsuario(correo, contraseña);

        String codigo = CorreoService.generarCodigoDeVerificacion();
        CorreoService.enviarCodigoPorCorreo(correo, codigo);

        boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);

        if(usuario!=null && verificado){

            Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
            UIHelper.abrirVentana(ventana_actual, "/view/admin/admin.fxml", "Administrador");

        }else {

            UIHelper.mostrarAlerta("Error", "Correo o contraseña incorrectos.");
        }
    }

    @FXML
    private void AbrirRegistro(){

        Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
        UIHelper.abrirVentana(ventana_actual, "/view/general/registro.fxml", "Registro");
    }

    @FXML
    private void RecuperarContraseña(){

        Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
        UIHelper.abrirVentana(ventana_actual, "/view/general/recuperarContraseña.fxml", "Recuperación de Contraseña");
    }
}
