package com.lectomundo.repository.dao;

import com.lectomundo.model.Notificacion;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.Timestamp;

public class NotificacionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarNotificacion(Notificacion notificacion) throws Exception {
        
        String sql = "INSERT INTO notificacion (id_usuario, tipo, mensaje, fecha_envio, es_leido) VALUES (?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, notificacion.getCliente().getId_usuario(), notificacion.getTipo(), notificacion.getMensaje(), Timestamp.valueOf(notificacion.getFecha_envio()), notificacion.isEs_leido());
    }
}
