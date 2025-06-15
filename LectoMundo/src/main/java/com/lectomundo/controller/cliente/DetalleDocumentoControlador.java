package com.lectomundo.controller.cliente;

import com.lectomundo.logic.AlquilerService;
import com.lectomundo.logic.CompraDocumentoService;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Documento;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetalleDocumentoControlador {

    @FXML private ImageView imgPortada;
    @FXML private Label lblTitulo;
    @FXML private Label lblAutor;
    @FXML private Label lblGenero;
    @FXML private Label lblDescripcion;
    @FXML private Button btnAlquilar;
    @FXML private Button btnComprar;
    @FXML private Button btnLeer;
    @FXML private Button btnDevolver;

    private Documento documento;

    @FXML
    private void initialize(Documento documento, boolean tiene_membresia, boolean esta_comprado, boolean esta_alquilado) throws Exception {
        this.documento = documento;

        MembresiaService membresiaService = new MembresiaService();
        CompraDocumentoService compraDocumentoService = new CompraDocumentoService();
        AlquilerService alquilerService = new AlquilerService();

        tiene_membresia = membresiaService.tieneMembresiaActiva(ClienteControlador.cliente.getId_usuario());
        esta_comprado = compraDocumentoService.estaComprado(ClienteControlador.cliente.getId_usuario(), documento.getId_documento());
        esta_alquilado = alquilerService.estaAlquilado(ClienteControlador.cliente.getId_usuario(), documento.getId_documento());

        imgPortada.setImage(new Image(documento.getPortada_url()));
        lblTitulo.setText(documento.getTitulo());
        lblAutor.setText("Autor: " + documento.getAutor());
        lblGenero.setText("Género: " + documento.getGenero());
        lblDescripcion.setText("Descripción: " + documento.getDescripcion());

        if(tiene_membresia || esta_comprado){

            btnAlquilar.setVisible(false);
            btnComprar.setVisible(false);
            btnLeer.setVisible(true);
        } else if (esta_alquilado){

            btnAlquilar.setVisible(false);
            btnDevolver.setVisible(true);
            btnLeer.setVisible(true);
        }
    }

}
