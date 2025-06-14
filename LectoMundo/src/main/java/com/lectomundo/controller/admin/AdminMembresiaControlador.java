package com.lectomundo.controller.admin;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Membresia;
import com.lectomundo.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class AdminMembresiaControlador {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Membresia> tblMembresias;
    @FXML private TableColumn<Membresia, Integer> colId;
    @FXML private TableColumn<Membresia, Integer> colIdCliente;
    @FXML private TableColumn<Membresia, Date> colFechaInicio;
    @FXML private TableColumn<Membresia, Date> colFechaFin;
    @FXML private TableColumn<Membresia, Integer> colCosto;

    MembresiaService membresiaService = new MembresiaService();

    @FXML
    private void initialize(){

        configurarColumnas();
        cargarMembresias();
    }

    private void configurarColumnas(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id_membresia"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fecha_fin"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    private void cargarMembresias(){

        try{

            ObservableList<Membresia> membresias = membresiaService.verMembresias();
            tblMembresias.setItems(membresias);

        }catch (Exception e){
            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo cargar las membresias.");
        }
    }

}
