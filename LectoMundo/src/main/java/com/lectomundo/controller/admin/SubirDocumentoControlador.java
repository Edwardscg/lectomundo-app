package com.lectomundo.controller.admin;

import com.lectomundo.controller.BackBlazeUploader;
import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;

public class SubirDocumentoControlador {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextArea areaDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private DatePicker pickerFechaPublicacion;
    @FXML
    private Label lblNombrePDF;
    @FXML
    private Label lblNombrePortada;

    private File archivoPDF;
    private File imagenPortada;

    @FXML
    public void seleccionarPDF() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar documento PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        archivoPDF = fileChooser.showOpenDialog(new Stage());

        if (archivoPDF != null) {

            lblNombrePDF.setText(archivoPDF.getName());
        } else {

            lblNombrePDF.setText("Ningun archivo seleccionado.");
        }
    }

    @FXML
    public void seleccionarPortada() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de portada");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.jpeg", "*.png", "*.webp"));
        imagenPortada = fileChooser.showOpenDialog(new Stage());

        if (imagenPortada != null) {

            lblNombrePortada.setText(imagenPortada.getName());
        } else {

            lblNombrePortada.setText("Ninguna imagen seleccionada.");
        }
    }

    @FXML
    public void subirDocumento() {

        if (txtTitulo.getText().isBlank() || txtAutor.getText().isBlank() || txtTipo.getText().isBlank() || txtGenero.getText().isBlank() || areaDescripcion.getText().isBlank() || txtPrecio.getText().isBlank() || pickerFechaPublicacion.getValue() == null || archivoPDF == null || imagenPortada == null) {

            UIHelper.mostrarAlerta("Campos Incompletos", "Complete todos los campos y selecciona los archivos.");
            return;
        }

        try{

            int precio = Integer.parseInt(txtPrecio.getText());
            if(precio <= 0){

                UIHelper.mostrarAlerta("Advertencia", "Complete todos los campos y selecciona los archivos.");
                return;
            }

        }catch (NumberFormatException e){

            UIHelper.mostrarAlerta("Error", "El número ingresado no es válido.");
        }

        try {

            BackBlazeUploader uploader = new BackBlazeUploader();
            String nombre_pdf = "documentos/" + System.currentTimeMillis() + "_" + archivoPDF.getName();
            String nombre_portada = "portadas/" + System.currentTimeMillis() + "_" + imagenPortada.getName();

            String pdf_url = uploader.getPublicUrl(uploader.uploadFile(archivoPDF, nombre_pdf, "application/pdf"));
            String portada_url = uploader.getPublicUrl(uploader.uploadFile(imagenPortada, nombre_portada, Files.probeContentType(imagenPortada.toPath())));

            Documento documento = new Documento(0, txtTitulo.getText(), txtAutor.getText(), txtTipo.getText(), pickerFechaPublicacion.getValue(), txtGenero.getText(), areaDescripcion.getText(), pdf_url, portada_url, Integer.parseInt(txtPrecio.getText()), 0, 0);

            DocumentoService documentoService = new DocumentoService();
            documentoService.registrarDocumento(documento);

            UIHelper.mostrarAlerta("Éxito", "El documento ha sido subido correctamente.");
            limpiarCampos();

        }catch (RuntimeException e){

            UIHelper.mostrarAlerta("Advertencia", e.getMessage());
        }catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo subir el documento.");
        }
    }

    private void limpiarCampos() {

        txtTitulo.clear();
        txtAutor.clear();
        txtTipo.clear();
        pickerFechaPublicacion.setValue(null);
        txtGenero.clear();
        areaDescripcion.clear();
        archivoPDF = null;
        imagenPortada = null;
        lblNombrePDF.setText("Ningún archivo seleccionado");
        lblNombrePortada.setText("Ninguna imagen seleccionada");
        txtPrecio.clear();
    }

}
