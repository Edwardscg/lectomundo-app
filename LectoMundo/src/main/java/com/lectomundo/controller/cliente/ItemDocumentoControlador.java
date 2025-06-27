package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.model.Documento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ItemDocumentoControlador {

    @FXML
    private ImageView imgPortada;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDescripcion;

    private Documento documento;

    public void mostrarDocumento(Documento documento) {

        this.documento = documento;

        lblTitulo.setText(documento.getTitulo());
        lblDescripcion.setText(documento.getDescripcion());

        try {

            Image portada = new Image(documento.getPortada_url(), true);
            imgPortada.setImage(portada);

        } catch (Exception e) {

            imgPortada.setImage(null);
        }
    }

    @FXML
    private void verDetalle() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/detalleDocumento.fxml"));
            Parent root = loader.load();

            DetalleDocumentoControlador detalleDocumentoControlador = loader.getController();
            detalleDocumentoControlador.cargarDatos(documento);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo mostrar la ventana con los detalles del documento.");
        }
    }
}
