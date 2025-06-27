package com.lectomundo.controller.cliente;

import com.lectomundo.model.Valoracion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ItemValoracionControlador {

    @FXML private Label lblUsuario;
    @FXML private Label lblComentario;
    @FXML private Label lblFecha;
    @FXML private HBox hboxEstrellas;

    public void cargarValoracion(Valoracion valoracion){

        lblUsuario.setText(valoracion.getCliente().getNombre_usuario());
        lblComentario.setText(valoracion.getComentario());
        lblFecha.setText(valoracion.getFecha_valoracion().toString());

        hboxEstrellas.getChildren().clear();

        for(int i = 1; i<=5; i++){

            Button estrella = new Button(i <= valoracion.getPuntuacion() ? "★" : "☆");
            estrella.setStyle("-fx-font-size: 12pt; -fx-background-color: transparent;");
            estrella.setDisable(true);
            hboxEstrellas.getChildren().add(estrella);
        }
    }
}
