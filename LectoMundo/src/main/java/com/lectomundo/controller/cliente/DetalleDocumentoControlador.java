package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.AlquilerService;
import com.lectomundo.logic.CompraDocumentoService;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DetalleDocumentoControlador {

    @FXML
    private ImageView imgPortada;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblGenero;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnAlquilar;
    @FXML
    private Button btnComprar;
    @FXML
    private Button btnLeer;
    @FXML
    private Button btnDevolver;
    @FXML
    private Button btnDescargar;

    private Documento documento;
    private Cliente cliente = ClienteControlador.cliente;
    private MembresiaService membresiaService = new MembresiaService();
    private CompraDocumentoService compraDocumentoService = new CompraDocumentoService();
    private AlquilerService alquilerService = new AlquilerService();
    private boolean tiene_membresia = false;
    private boolean esta_comprado = false;
    private boolean esta_alquilado = false;

    @FXML
    private void initialize() {

    }

    @FXML
    private void alquilarDocumento() {

        try {

            alquilerService.registrarAlquiler(cliente, documento);

            btnAlquilar.setVisible(false);
            btnDevolver.setVisible(true);
            btnLeer.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void devolverDocumento() {

        try {

            alquilerService.devolverDocumento(cliente, documento);

            btnAlquilar.setVisible(true);
            btnDevolver.setVisible(false);
            btnLeer.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void comprarDocumento() {

        try {

            compraDocumentoService.registrarCompra(cliente, documento);

            btnAlquilar.setVisible(false);
            btnComprar.setVisible(false);
            btnDevolver.setVisible(false);
            btnLeer.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void leerDocumento() {

        try {
            String url = documento.getPdf_url();
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo abrir el documento en el navegador.");
        }
    }

    @FXML
    private void descargarDocumento(){

        try{

            String url = documento.getPdf_url();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Descargar Documento");
            fileChooser.setInitialFileName(documento.getTitulo().replaceAll("\\s+","_") + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File destino = fileChooser.showSaveDialog(btnDescargar.getScene().getWindow());

            if(destino != null){

                try(InputStream inputStream = new URL(url).openStream();
                    OutputStream outputStream = new FileOutputStream(destino)){

                    byte[] buffer = new byte[4096];
                    int bytes_read;

                    while((bytes_read = inputStream.read(buffer)) != -1){

                        outputStream.write(buffer, 0, bytes_read);

                    }
                    UIHelper.mostrarAlerta("Éxito", "El documento se ha descargado.");
                }
            }
        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo descargar el documento");
        }
    }

    public void cargarDatos(Documento documento) {

        this.documento = documento;

        try {

            tiene_membresia = membresiaService.tieneMembresiaActiva(cliente.getId_usuario());
            esta_comprado = compraDocumentoService.estaComprado(cliente.getId_usuario(), documento.getId_documento());
            esta_alquilado = alquilerService.estaAlquilado(cliente.getId_usuario(), documento.getId_documento());

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
                btnDescargar.setVisible(esta_comprado);
            } else if (esta_alquilado) {
                btnAlquilar.setVisible(false);
                btnDevolver.setVisible(true);
                btnLeer.setVisible(true);
            } else {

                btnAlquilar.setVisible(true);
                btnComprar.setVisible(true);
                btnLeer.setVisible(false);
                btnDevolver.setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo cargar los datos del documento.");
        }
    }
}
