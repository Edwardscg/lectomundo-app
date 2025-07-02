package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

public class ValoracionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarValoracion(Valoracion valoracion) {

        String sql = "INSERT INTO valoracion (id_usuario, id_documento, puntuacion, comentario) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, valoracion.getCliente().getId_usuario(), valoracion.getDocumento().getId_documento(), valoracion.getPuntuacion(), valoracion.getComentario());
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public void actualizarValoracion(Valoracion valoracion) {

        String sql = "UPDATE valoracion SET puntuacion = ?, comentario = ?, fecha_valoracion = ? WHERE id_usuario = ? AND id_documento = ?;";

        DBHelper.manejarEntidad(sql, valoracion.getPuntuacion(), valoracion.getComentario(), Date.valueOf(valoracion.getFecha_valoracion()), valoracion.getCliente().getId_usuario(), valoracion.getDocumento().getId_documento());
    }

    // IMPLEMENTACION EN UN FUTURO
    public void eliminarValoracion(int id_usuario, int id_documento) {
        String sql = "DELETE FROM valoracion WHERE id_usuario = ? AND id_documento = ?;";

        DBHelper.manejarEntidad(sql, id_usuario, id_documento);
    }
    */

    public List<Valoracion> verValoracionesPorDocumento(int id_documento) {

        String sql = "SELECT * FROM valoracion WHERE id_documento = ? ORDER BY fecha_valoracion DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearValoracion, id_documento);
    }

    public float obtenerPromedioValoracion(int id_libro) {

        String sql = "SELECT AVG(puntuacion) AS promedio FROM valoracion WHERE id_documento = ?;";

        Float promedio = DBHelper.obtenerEntidad(sql, rs -> rs.getFloat("promedio"), id_libro);

        return promedio != null ? promedio : 0;
    }

    public int contarValoracionesPorDocumento(int id_documento) {
        String sql = "SELECT COUNT(*) AS total FROM valoracion WHERE id_documento = ?;";

        Integer total = DBHelper.obtenerEntidad(sql, rs -> rs.getInt("total"), id_documento);

        return total != null ? total : 0;
    }

    private Valoracion mapearValoracion(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
            Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

            return new Valoracion(rs.getInt("id_valoracion"), (Cliente) usuario, documento, rs.getInt("puntuacion"), rs.getString("comentario"), rs.getDate("fecha_valoracion").toLocalDate());

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos.");
        }
    }
}
