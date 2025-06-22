package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class NotificacionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarNotificacion(Notificacion notificacion) {

        String sql = "INSERT INTO notificacion (id_usuario, tipo, mensaje, fecha_envio, es_leido) VALUES (?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, notificacion.getCliente().getId_usuario(), notificacion.getTipo(), notificacion.getMensaje(), Timestamp.valueOf(notificacion.getFecha_envio()), notificacion.isEs_leido());
    }

    public List<Notificacion> obtenerNotificacionesPorUsuario(int id_usuario) {

        String sql = "SELECT * FROM notificacion WHERE id_usuario = ? ORDER BY fecha_envio DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearNotificacion, id_usuario);
    }

    public void marcarNotificacionComoLeida(int id_notificacion) {

        String sql = "UPDATE notificacion SET es_leido = TRUE WHERE id_notificacion = ?;";

        DBHelper.manejarEntidad(sql, id_notificacion);
    }

    private Notificacion mapearNotificacion(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new Notificacion(rs.getInt("id_notificacion"), (Cliente) usuario, rs.getString("tipo"), rs.getString("mensaje"), rs.getTimestamp("fecha_envio").toLocalDateTime(), rs.getBoolean("es_leido"));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de la notificación desde la Base de Datos.");
        }
    }
}
