package com.lectomundo.repository.dao;

import com.lectomundo.model.Valoracion;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;

public class ValoracionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarValoracion(Valoracion valoracion) throws Exception {

        String sql = "INSERT INTO valoracion (id_usuario, id_documento, puntuacion, comentario, fecha_valoracion) VALUES (?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, valoracion.getCliente().getId_usuario(), valoracion.getDocumento().getId_documento(), valoracion.getPuntuacion(), valoracion.getComentario(), Date.valueOf(valoracion.getFecha_valoracion()));
    }

    public void actualizarValoracion(Valoracion valoracion) throws Exception {

        String sql = "UPDATE valoracion SET puntuacion = ?, comentario = ?, fecha_valoracion = ? WHERE id_usuario = ? AND id_documento = ?;";

        DBHelper.manejarEntidad(sql, valoracion.getPuntuacion(), valoracion.getComentario(), Date.valueOf(valoracion.getFecha_valoracion()), valoracion.getCliente().getId_usuario(), valoracion.getDocumento().getId_documento());
    }

    public void eliminarValoracion(int id_usuario, int id_documento) throws Exception {
        String sql = "DELETE FROM valoracion WHERE id_usuario = ? AND id_documento = ?;";

        DBHelper.manejarEntidad(sql, id_usuario, id_documento);
    }
}
