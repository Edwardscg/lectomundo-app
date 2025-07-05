package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Notificacion;
import com.lectomundo.repository.dao.NotificacionDAO;

import java.util.List;

public class NotificacionService {

    private NotificacionDAO notificacionDAO = new NotificacionDAO();

    public void registrarNotificacion(Cliente cliente, String tipo, String mensaje) {

        Notificacion notificacion = new Notificacion();
        notificacion.setCliente(cliente);
        notificacion.setTipo(tipo);
        notificacion.setMensaje(mensaje);

        notificacionDAO.registrarNotificacion(notificacion);
    }

    public List<Notificacion> obtenerNotificacionesPorCliente(int id_cliente) {

        return notificacionDAO.obtenerNotificacionesPorUsuario(id_cliente);
    }

    public void marcarComoLeida(int id_notificacion) {

        notificacionDAO.marcarNotificacionComoLeida(id_notificacion);
    }

    public void notificacionCompraDocumento(Cliente cliente, Documento documento) {

        String mensaje = "Has comprado el documento: " + documento.getTitulo();
        registrarNotificacion(cliente, "Compra", mensaje);
    }

    public void notificacionAlquilerDocumento(Cliente cliente, Documento documento) {

        String mensaje = "Has alquilado el documento: " + documento.getTitulo();
        registrarNotificacion(cliente, "Alquiler", mensaje);
    }
}
