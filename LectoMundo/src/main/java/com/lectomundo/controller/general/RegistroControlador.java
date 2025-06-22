package com.lectomundo.controller.general;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.CorreoService;
import com.lectomundo.logic.UsuarioService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroControlador {


    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtContraseña;
    @FXML private TextField txtConfirmarContraseña;

    @FXML
    private void Registrarse() throws Exception{

        try{

            String nombre = txtNombreUsuario.getText().trim();
            String correo = txtCorreo.getText().trim();
            String contraseña = txtContraseña.getText();
            String confirmar = txtConfirmarContraseña.getText();

            if(nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmar.isEmpty()){

                UIHelper.mostrarAlerta("Error", "Todos los datos son obligatorios.");
                return;
            }

            if(!contraseña.equals(confirmar)){

                UIHelper.mostrarAlerta("Error", "Las contraseñas no coinciden.");
                return;
            }

            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.buscarUsuarioPorCorreo(correo);

            if(usuario!= null){

                UIHelper.mostrarAlerta("Error", "Ya existe un usuario registrado con ese correo.");
                return;
            }

            String codigo = CorreoService.generarCodigoDeVerificacion();
            CorreoService.enviarCodigoPorCorreo(correo, codigo);

            boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);

            if(verificado){

                Cliente cliente = new Cliente(0, nombre, correo, contraseña, "cliente", 0);
                usuarioService.registrarUsuario(cliente);

                Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
                UIHelper.abrirVentana(ventana_actual, "/view/general/login.fxml", "Login");
            }else{

                UIHelper.mostrarAlerta("Error", "No se completó la verificación.");
            }

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo realizar el registro.");
        }
    }

    @FXML
    private void Cancelar(){

        try{

            Stage ventana_actual = (Stage) txtCorreo.getScene().getWindow();
            UIHelper.abrirVentana(ventana_actual, "/view/general/login.fxml", "Login");

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo volver a login.");
        }
    }
}
