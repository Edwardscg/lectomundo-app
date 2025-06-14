package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.UsuarioService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminUsuariosControlador {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    //@FXML private TableColumn<Usuario, String> colContraseña;
    @FXML private TableColumn<Usuario, String> colTipo;
    @FXML private TableColumn<Usuario, String> colMonedas;

    UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize(){

        configurarColumnas();
        cargarUsuarios();
    }

    private void configurarColumnas(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_usuario"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        //colContraseña.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_usuario"));
        colMonedas.setCellValueFactory(data -> {

            Usuario usuario = data.getValue();
            if(usuario instanceof Cliente cliente){
                return new SimpleStringProperty(String.valueOf(cliente.getMonedas()));
            }else {

                return new SimpleStringProperty("");
            }
        });
    }

    private void cargarUsuarios(){

        try{

            ObservableList<Usuario> usuarios = usuarioService.verUsuarios();
            tblUsuarios.setItems(usuarios);
        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo cargar los usuarios.");
        }
    }

}
