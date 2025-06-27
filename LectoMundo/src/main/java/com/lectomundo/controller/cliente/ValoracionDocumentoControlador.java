package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.ValoracionService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Valoracion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

public class ValoracionDocumentoControlador {

    @FXML private TextArea txtComentario;
    @FXML private VBox vboxValoraciones;
    @FXML private Button estrella1, estrella2, estrella3, estrella4, estrella5;

    private int puntuacion = 0;
    private Cliente cliente;
    private Documento documento;
    private ValoracionService valoracionService = new ValoracionService();

    public void cargarDatos(Cliente cliente, Documento documento){

        this.documento = documento;
        this.cliente = cliente;
        cargarValoraciones();
    }

    @FXML private void seleccionarEstrella1() {

        puntuacion = 1;
        actualizarEstrellas();
    }
    @FXML private void seleccionarEstrella2() {

        puntuacion= 2;
        actualizarEstrellas();
    }
    @FXML private void seleccionarEstrella3() {

        puntuacion = 3;
        actualizarEstrellas();
    }
    @FXML private void seleccionarEstrella4() {

        puntuacion = 4;
        actualizarEstrellas();
    }
    @FXML private void seleccionarEstrella5() {

        puntuacion = 5;
        actualizarEstrellas();
    }

    private void actualizarEstrellas(){

        Button[] estrellas = {estrella1, estrella2, estrella3, estrella4, estrella5};

        for(int i = 0; i < estrellas.length; i++){

            estrellas[i].setText(i<puntuacion ? "★" : "☆");
        }
    }

    @FXML
    private void valorarDocumento(){

        String comentario = txtComentario.getText().trim();

        if(puntuacion == 0){

            UIHelper.mostrarAlerta("Aviso", "Debes seleccionar una puntuación.");
            return;
        }

        if(comentario.isEmpty()){

            UIHelper.mostrarAlerta("Aviso", "El comentario no puede estar vacío.");
            return;
        }

        try{

            valoracionService.registrarValoracion(cliente, documento, comentario, puntuacion);

            UIHelper.mostrarAlerta("Éxito", "Tu valoración ha sido registrada exitosamente.");

            //txtComentario.clear();
            //puntuacion = 0;
            actualizarEstrellas();
            cargarValoraciones();
        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo registrar la valoración.");
        }
    }

    private void cargarValoraciones(){

        try{

            vboxValoraciones.getChildren().clear();

            List<Valoracion> valoraciones = valoracionService.verValoracionesPorDocumento(documento.getId_documento());

            for(Valoracion v : valoraciones){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemValoracion.fxml"));
                Node item = loader.load();

                ItemValoracionControlador itemValoracionControlador = loader.getController();
                itemValoracionControlador.cargarValoracion(v);

                vboxValoraciones.getChildren().add(item);
            }
        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar las valoraciones.");
        }
    }

}
