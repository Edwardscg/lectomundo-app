package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

public class AdminControlador {

    @FXML private StackPane paneContenido;

    @FXML
    private void cargarDocumentos(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin/adminDocumentos.fxml"));
            Parent root = loader.load();
            paneContenido.getChildren().setAll(root);

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los documentos.");
        }
    }

    @FXML
    private void cargarUsuarios(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin/adminUsuarios.fxml"));
            Parent root = loader.load();
            paneContenido.getChildren().setAll(root);
        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los usuarios.");
        }
    }


}
