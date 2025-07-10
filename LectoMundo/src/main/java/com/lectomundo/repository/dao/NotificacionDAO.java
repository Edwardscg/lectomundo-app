package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.util.List;

/**
 * DAO que gestiona las operaciones relacionadas con notificaciones enviadas a los clientes en la app.
 * Permite registrar nuevas notificaciones, marcarlas como leídas y obtenerlas por usuario.
 */
public class NotificacionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Registra una nueva notificación en la base de datos.
     *
     * @param notificacion Objeto Notificacion que contiene el mensaje, tipo y estado de lectura.
     */
    public void registrarNotificacion(Notificacion notificacion) {

        String sql = "INSERT INTO notificacion (id_usuario, tipo, mensaje, es_leido) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql,
                notificacion.getCliente().getId_usuario(),
                notificacion.getTipo(),
                notificacion.getMensaje(),
                notificacion.isEs_leido());
    }

    /**
     * Obtiene todas las notificaciones enviadas a un cliente específico, ordenadas por fecha de envío descendente.
     *
     * @param id_usuario ID del cliente al que pertenecen las notificaciones.
     * @return Lista de notificaciones asociadas al cliente.
     */
    public List<Notificacion> obtenerNotificacionesPorUsuario(int id_usuario) {

        String sql = "SELECT * FROM notificacion WHERE id_usuario = ? ORDER BY fecha_envio DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearNotificacion, id_usuario);
    }

    /**
     * Marca una notificación como leída en la base de datos.
     *
     * @param id_notificacion ID de la notificación que se va a marcar como leída.
     */
    public void marcarNotificacionComoLeida(int id_notificacion) {

        String sql = "UPDATE notificacion SET es_leido = TRUE WHERE id_notificacion = ?;";

        DBHelper.manejarEntidad(sql, id_notificacion);
    }

    /**
     * Mapea una fila del ResultSet a un objeto Notificacion.
     *
     * @param rs ResultSet con los datos obtenidos de la consulta.
     * @return Objeto Notificacion con los datos correspondientes.
     */
    private Notificacion mapearNotificacion(ResultSet rs) {

        try {

            Cliente cliente = (Cliente)usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new Notificacion(rs.getInt("id_notificacion"), cliente, rs.getString("tipo"), rs.getString("mensaje"), rs.getTimestamp("fecha_envio").toLocalDateTime(), rs.getBoolean("es_leido"));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de la notificación desde la Base de Datos.");
        }
    }
}
