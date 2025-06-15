package com.lectomundo.controller.cliente;

import com.lectomundo.model.Documento;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemDocumentoControlador {

    @FXML private ImageView imgPortada;
    @FXML private Label lblTitulo;
    @FXML private Label lblDescripcion;
    @FXML private Button btnVer;

    private Documento documento;

    public void mostrarDocumento(Documento documento){

        this.documento = documento;

        lblTitulo.setText(documento.getTitulo());
        lblDescripcion.setText(documento.getDescripcion());

        try{

            Image portada = new Image(documento.getPortada_url(), true);
            imgPortada.setImage(portada);

        }catch (Exception e){
            e.printStackTrace();
            imgPortada.setImage(null);
        }
    }
}
