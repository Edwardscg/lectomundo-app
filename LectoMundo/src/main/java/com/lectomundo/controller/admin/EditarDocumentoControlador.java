package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarDocumentoControlador {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtTipo;
    @FXML
    private DatePicker pickerFechaPublicacion;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextArea areaDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtPdf;
    @FXML
    private TextField txtPortada;

    private Documento documento;
    private DocumentoService documentoService = new DocumentoService();

    public void cargarDocumento(Documento documento) {

        this.documento = documento;
        llenarCampos(documento);
    }

    private void llenarCampos(Documento documento) {

        txtTitulo.setText(documento.getTitulo());
        txtAutor.setText(documento.getAutor());
        txtTipo.setText(documento.getTipo_documento());
        pickerFechaPublicacion.setValue(documento.getFecha_publicacion());
        txtGenero.setText(documento.getGenero());
        areaDescripcion.setText(documento.getDescripcion());
        txtPrecio.setText(String.valueOf(documento.getPrecio()));
        txtPdf.setText(documento.getPdf_url());
        txtPortada.setText(documento.getPortada_url());

        txtPdf.setDisable(true);
        txtPortada.setDisable(true);

    }

    @FXML
    private void guardarCambios() {

        if (txtTitulo.getText().isBlank() || txtAutor.getText().isBlank() || txtTipo.getText().isBlank() || pickerFechaPublicacion.getValue() == null || txtGenero.getText().isBlank() || areaDescripcion.getText().isBlank() || txtPrecio.getText().isBlank() ) {

            UIHelper.mostrarAlerta("Campos Incompletos", "Complete todos los campos.");
            return;
        }

        try{

            int precio = Integer.parseInt(txtPrecio.getText());
            if(precio <= 0){

                UIHelper.mostrarAlerta("Advertencia", "El precio debe ser mayor a 0.");
                return;
            }

        }catch (NumberFormatException e){

            UIHelper.mostrarAlerta("Error", "El número ingresado no es válido.");
        }

        try {

            documento.setTitulo(txtTitulo.getText());
            documento.setAutor(txtAutor.getText());
            documento.setTipo_documento(txtTipo.getText());
            documento.setFecha_publicacion(pickerFechaPublicacion.getValue());
            documento.setGenero(txtGenero.getText());
            documento.setDescripcion(areaDescripcion.getText());
            documento.setPrecio(Integer.parseInt(txtPrecio.getText()));
            documento.setPdf_url(txtPdf.getText());
            documento.setPortada_url(txtPortada.getText());

            documentoService.actualizarDocumento(documento);

            UIHelper.mostrarAlerta("Éxito", "Documento actualizado correctamente.");

            Stage ventana_actual = (Stage) txtTitulo.getScene().getWindow();
            UIHelper.cerrarVentana(ventana_actual);

        } catch (NumberFormatException e) {

            UIHelper.mostrarAlerta("Error", "El precio debe ser un número valido.");
        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo actualizar el documento.");
        }
    }

    @FXML
    private void cancelar() {

        Stage ventana_actual = (Stage) txtTitulo.getScene().getWindow();
        UIHelper.cerrarVentana(ventana_actual);
    }
}
