package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Notificacion;
import com.lectomundo.repository.dao.NotificacionDAO;

public class NotificacionService {

    private NotificacionDAO notificacionDAO = new NotificacionDAO();

    public void registrarNotificacion(Cliente cliente, String tipo, String mensaje){

        Notificacion notificacion = new Notificacion();
        notificacion.setCliente(cliente);
        notificacion.setTipo(tipo);
        notificacion.setMensaje(mensaje);

        notificacionDAO.registrarNotificacion(notificacion);
    }


}
