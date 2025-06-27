package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.NotificacionService;
import com.lectomundo.model.Notificacion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ItemNotificacionControlador {

    @FXML private Label lblTipo;
    @FXML private Label lblMensaje;
    @FXML private Label lblFecha;
    @FXML private Button btnMarcarLeida;
    @FXML private HBox caja;

    private Notificacion notificacion;
    private NotificacionService notificacionService = new NotificacionService();

    public void colocarDatos(Notificacion notificacion){

        this.notificacion = notificacion;

        lblTipo.setText(notificacion.getTipo());
        lblMensaje.setText(notificacion.getMensaje());
        lblFecha.setText(notificacion.getFecha_envio().toLocalDate().toString());

        if(notificacion.isEs_leido()){

            btnMarcarLeida.setVisible(false);
            caja.setStyle("-fx-border-color: #cccccc;");
        }else{

            switch (notificacion.getTipo()){
                case "Compra", "Alquiler":
                    caja.setStyle("-fx-border-color: #27ae60; -fx-border-width: 2;");
                    break;
                case "Aviso":
                    caja.setStyle("-fx-border-color: #e67e22; -fx-border-width: 2;");
                    break;
                default:
                    caja.setStyle("-fx-border-color: #3498db; -fx-border-width: 2;");
            }
        }
    }

    @FXML
    private void marcarComoLeida(){

        try{

            notificacionService.marcarComoLeida(notificacion.getId_notificacion());
            btnMarcarLeida.setVisible(false);
            caja.setStyle("-fx-border-color: #cccccc;");

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo marcar la notificación como leída.");
        }
    }
}
