package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.model.Documento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.control.cell.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AdminDocumentosControlador {

    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnSubirDocumento;
    @FXML private TableView<Documento> tblDocumentos;
    @FXML private TableColumn<Documento, Integer> colId;
    @FXML private TableColumn<Documento, String> colTitulo;
    @FXML private TableColumn<Documento, String> colAutor;
    @FXML private TableColumn<Documento, String> colTipo;
    @FXML private TableColumn<Documento, String> colFecha;
    @FXML private TableColumn<Documento, String> colGenero;
    @FXML private TableColumn<Documento, Integer> colPrecio;
    @FXML private TableColumn<Documento, Float> colPuntuacion;
    @FXML private TableColumn<Documento, Integer> colValoraciones;

    private ObservableList<Documento> listaDocumentos;
    private DocumentoService documentoService = new DocumentoService();

    @FXML
    public void initialize(){

        configurarColumnas();
        cargarDocumentos();
    }

    @FXML
    private void irASubirDocumento(){

        Stage ventana_actual = (Stage) txtBuscar.getScene().getWindow();
        UIHelper.abrirVentana(ventana_actual, "/view/admin/subirDocumento.fxml", "Subir Documento");
    }

    private void configurarColumnas(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id_documento"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_documento"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_publicacion")); // Asegúrate que sea tipo String o LocalDate
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion_promedio"));
        colValoraciones.setCellValueFactory(new PropertyValueFactory<>("cantidad_valoraciones"));
    }

    private void cargarDocumentos(){

        try{

            ObservableList<Documento> documentos = documentoService.verDocumentos();
            tblDocumentos.setItems(documentos);
        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo cargar los documentos");
        }
    }


}
