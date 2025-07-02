package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.FavoritoService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class DocumentosFavoritosControlador {

    @FXML VBox vboxContenedor;
    @FXML Label lblMensaje;

    private FavoritoService favoritoService = new FavoritoService();
    private Cliente cliente = ClienteControlador.cliente;

    @FXML
    private void initialize(){

        cargarDocumentosFavoritos();
    }

    private void cargarDocumentosFavoritos(){

        try{

            List<Documento> documentosFavoritos = favoritoService.obtenerFavoritosPorUsuario(cliente);

            if(documentosFavoritos.isEmpty()){

                lblMensaje.setText("No cuentas con documentos favoritos marcados");
                return;
            }

            HBox fila = new HBox(35);
            int contador = 0;

            for(Documento documento : documentosFavoritos){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemDocumento.fxml"));
                Node item = loader.load();

                ItemDocumentoControlador itemDocumentoControlador = loader.getController();
                itemDocumentoControlador.mostrarDocumento(documento);

                fila.getChildren().add(item);
                contador++;

                if(contador%3 ==0){

                    vboxContenedor.getChildren().add(fila);
                    fila = new HBox(35);
                }
            }

            if(!fila.getChildren().isEmpty()){

                vboxContenedor.getChildren().add(fila);
            }

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los documentos Favoritos.");
        }
    }
}
