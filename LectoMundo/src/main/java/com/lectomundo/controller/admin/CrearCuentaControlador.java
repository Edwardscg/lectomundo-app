package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CrearCuentaControlador {

    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContraseña;
    @FXML private PasswordField txtConfirmarContraseña;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML private void registrarse(){

        String nombre_usuario = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        String confirmar_contraseña = txtConfirmarContraseña.getText().trim();
        String tipo_usuario = "administrador";

        if(nombre_usuario.isBlank() || correo.isBlank() || contraseña.isBlank() || confirmar_contraseña.isBlank()){

            UIHelper.mostrarAlerta("Advertencia", "Todos los campos deben ser llenados.");
            return;
        }

        if(!confirmar_contraseña.equals(confirmar_contraseña)){

            UIHelper.mostrarAlerta("Advertencia", "Las contrseñas no coinciden.");
            return;
        }

        try{

            usuarioService.registrarUsuario(nombre_usuario, correo, contraseña, tipo_usuario);

            UIHelper.mostrarAlerta("Éxito", "Se pudo registrar correctamente la cuenta de administrador.");

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo registrar la cuenta de administrador.");
        }
    }
}
