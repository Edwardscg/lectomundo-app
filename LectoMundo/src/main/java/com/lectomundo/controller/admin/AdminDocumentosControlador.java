package com.lectomundo.controller.admin;

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
    @FXML private TableView<Documento> tblDocumento;
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


}
