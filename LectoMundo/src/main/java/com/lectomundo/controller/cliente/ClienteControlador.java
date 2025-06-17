package com.lectomundo.controller.cliente;

import com.lectomundo.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ClienteControlador {

    @FXML private StackPane panelContenedor;

    public static Cliente cliente;

    private boolean menu_visible = true;

    public ClienteControlador() throws Exception {
    }

    @FXML
    private void initialize(){

        explorarDocumentos();
    }

    @FXML
    private void explorarDocumentos(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/explorarDocumentos.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @FXML
    private void verDocumentosAlquilados(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/documentosAlquilados.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @FXML
    private void verDocumentosComprados(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/documentosComprados.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

}
