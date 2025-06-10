package com.lectomundo.repository.dao;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Notificacion;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class NotificacionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarNotificacion(Notificacion notificacion) throws Exception {

        String sql = "INSERT INTO notificacion (id_usuario, tipo, mensaje, fecha_envio, es_leido) VALUES (?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, notificacion.getCliente().getId_usuario(), notificacion.getTipo(), notificacion.getMensaje(), Timestamp.valueOf(notificacion.getFecha_envio()), notificacion.isEs_leido());
    }

    public List<Notificacion> obtenerNotificacionesPorUsuario(int id_usuario) throws Exception {

        String sql = "SELECT * FROM notificacion WHERE id_usuario = ? ORDER BY fecha_envio DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearNotificacion, id_usuario);
    }

    private Notificacion mapearNotificacion(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

        return new Notificacion(rs.getInt("id_notificacion"), (Cliente) usuario, rs.getString("tipo"), rs.getString("mensaje"), rs.getTimestamp("fecha_envio").toLocalDateTime(), rs.getBoolean("es_leido"));
    }
}
