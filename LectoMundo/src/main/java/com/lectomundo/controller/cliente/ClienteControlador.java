package com.lectomundo.controller.cliente;

import com.lectomundo.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ClienteControlador {

    @FXML private StackPane panelContenedor;
    @FXML private Label lblMonedas;

    public static Cliente cliente;

    @FXML
    private void initialize(){

        explorarDocumentos();
        actualizarMonedas(cliente.getMonedas());
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

    @FXML
    private void irATiendaMonedas(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/compraMonedas.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

            CompraMonedasControlador compraMonedasControlador = loader.getController();
            compraMonedasControlador.setClienteControlador(this);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void actualizarMonedas(int nuevas_monedas){

        lblMonedas.setText(String.valueOf(nuevas_monedas));
    }

}
