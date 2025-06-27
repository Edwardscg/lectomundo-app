package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Notificacion;
import com.lectomundo.repository.dao.NotificacionDAO;

import java.util.List;

public class NotificacionService {

    private NotificacionDAO notificacionDAO = new NotificacionDAO();

    public void registrarNotificacion(Cliente cliente, String tipo, String mensaje){

        Notificacion notificacion = new Notificacion();
        notificacion.setCliente(cliente);
        notificacion.setTipo(tipo);
        notificacion.setMensaje(mensaje);

        notificacionDAO.registrarNotificacion(notificacion);
    }

    public List<Notificacion> obtenerNotificacionesPorUsuario(int id_usuario){

        return notificacionDAO.obtenerNotificacionesPorUsuario(id_usuario);
    }

    public void marcarComoLeida(int id_notificacion){

        notificacionDAO.marcarNotificacionComoLeida(id_notificacion);
    }

    public void notificacionCompraDocumento(Cliente cliente, Documento documento){

        String mensaje = "Has comprado el documento: " + documento.getTitulo();
        registrarNotificacion(cliente, "Compra", mensaje);
    }

    public void notificacionAlquilerDocumento(Cliente cliente, Documento documento){

        String mensaje = "Has alquilado el documento: " + documento.getTitulo();
        registrarNotificacion(cliente, "Alquiler", mensaje);
    }

    public void notificacionUltimoDiaAlquiler(Cliente cliente, Documento documento){

        String mensaje = "Hos es el ultimo día de alquiler del documento: " + documento.getTitulo();
        registrarNotificacion(cliente, "Aviso-Alquiler", mensaje);
    }

    public void notificacionUltimoDiaMembresia(Cliente cliente){

        String mensaje = "Hoy es el ultimo dia de tu membresía, renueva para seguir teniendo acceso completo a los documentos.";
        registrarNotificacion(cliente, "Aviso-Membresía", mensaje);
    }
}
