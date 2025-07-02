package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.NotificacionService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Notificacion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.List;

public class NotificacionesControlador {

    @FXML
    private VBox contenedorNotificaciones;

    private NotificacionService notificacionService = new NotificacionService();
    private Cliente cliente = ClienteControlador.cliente;

    @FXML
    private void initialize() {

        cargarNotificaciones();
    }

    private void cargarNotificaciones() {

        try {

            List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(cliente.getId_usuario());

            for (Notificacion n : notificaciones) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemNotificacion.fxml"));
                Node item = loader.load();

                ItemNotificacionControlador itemNotificacionControlador = loader.getController();
                itemNotificacionControlador.colocarDatos(n);

                contenedorNotificaciones.getChildren().add(item);
            }

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo cargar las notificaciones.");
        }
    }
}
