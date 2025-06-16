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
    private void initialize(){

    }

    public void cargarDatos(Documento documento){

        this.documento = documento;

        try {
            MembresiaService membresiaService = new MembresiaService();
            CompraDocumentoService compraDocumentoService = new CompraDocumentoService();
            AlquilerService alquilerService = new AlquilerService();

            boolean tiene_membresia = membresiaService.tieneMembresiaActiva(ClienteControlador.cliente.getId_usuario());
            boolean esta_comprado = compraDocumentoService.estaComprado(ClienteControlador.cliente.getId_usuario(), documento.getId_documento());
            boolean esta_alquilado = alquilerService.estaAlquilado(ClienteControlador.cliente.getId_usuario(), documento.getId_documento());

            imgPortada.setImage(new Image(documento.getPortada_url()));
            lblTitulo.setText(documento.getTitulo());
            lblAutor.setText("Autor: " + documento.getAutor());
            lblGenero.setText("Género: " + documento.getGenero());
            lblDescripcion.setText("Descripción: " + documento.getDescripcion());

            System.out.println("ID Cliente: " + ClienteControlador.cliente.getId_usuario());
            System.out.println("ID Documento: " + documento.getId_documento());
            System.out.println("Tiene membresía: " + tiene_membresia);
            System.out.println("Está comprado: " + esta_comprado);
            System.out.println("Está alquilado: " + esta_alquilado);

            if (tiene_membresia || esta_comprado) {
                btnAlquilar.setVisible(false);
                btnComprar.setVisible(false);
                btnLeer.setVisible(true);
            } else if (esta_alquilado) {
                btnAlquilar.setVisible(false);
                btnDevolver.setVisible(true);
                btnLeer.setVisible(true);
            }else {

                btnAlquilar.setVisible(true);
                btnComprar.setVisible(true);
                btnLeer.setVisible(false);
                btnDevolver.setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
