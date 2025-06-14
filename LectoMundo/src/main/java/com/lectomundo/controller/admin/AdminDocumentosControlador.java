package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.model.Documento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
    @FXML private Button btnEditarDocumento;
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
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_publicacion"));
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

    @FXML
    private void editarDocumentoSeleccionado(){

        Documento documentoSeleccionado = tblDocumentos.getSelectionModel().getSelectedItem();

        if(documentoSeleccionado == null){

            UIHelper.mostrarAlerta("Advertencia", "Selecciona un documento para editar");
            return;
        }

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin/editarDocumento.fxml"));
            Parent root = loader.load();

            EditarDocumentoControlador editarDocumentoControlador = loader.getController();
            editarDocumentoControlador.cargarDocumento(documentoSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Documento");
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Nose pudo abrir la ventana de edicion");
        }
    }


}
