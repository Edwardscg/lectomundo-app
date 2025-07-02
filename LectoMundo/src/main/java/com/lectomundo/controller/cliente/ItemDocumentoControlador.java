package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.model.Documento;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ItemDocumentoControlador {

    @FXML
    private ImageView imgPortada;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDescripcion;
    @FXML
    private StackPane infoBox;

    private Documento documento;
    private ClienteControlador clienteControlador;

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

    public void setClienteControlador(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;
    }

    @FXML
    private void verDetalle() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/detalleDocumento.fxml"));
            Parent root = loader.load();

            DetalleDocumentoControlador detalleDocumentoControlador = loader.getController();
            detalleDocumentoControlador.setClienteControlador(clienteControlador);
            detalleDocumentoControlador.cargarDatos(documento);

            Stage ventana_detalle = new Stage();
            ventana_detalle.setScene(new Scene(root));

            Stage ventana_principal = (Stage) ((Node) imgPortada).getScene().getWindow();
            ventana_principal.hide();


            ventana_detalle.setOnHiding(e -> {
                if (!ventana_principal.isShowing()) {
                    ventana_principal.show();
                }
            });

            ventana_detalle.setResizable(false);
            ventana_detalle.show();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo mostrar la ventana con los detalles del documento.");
        }
    }

    @FXML
    private void expandirItem() {

        infoBox.setVisible(true);
        infoBox.setManaged(true);

        Timeline animar = new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(infoBox.opacityProperty(), 1), new KeyValue(imgPortada.fitHeightProperty(), 270)));
        animar.play();
    }

    @FXML
    private void colapsarItem() {

        Timeline animar = new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(infoBox.opacityProperty(), 0), new KeyValue(imgPortada.fitHeightProperty(), 380)));

        animar.setOnFinished(e -> {
            infoBox.setVisible(false);
            infoBox.setManaged(false);
        });
        animar.play();
    }
}
