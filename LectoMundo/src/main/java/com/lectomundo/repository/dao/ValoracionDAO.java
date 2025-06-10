package com.lectomundo.repository.dao;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Usuario;
import com.lectomundo.model.Valoracion;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

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

    public List<Valoracion> verValoracionesPorDocumento(int id_documento) throws Exception {

        String sql = "SELECT * FROM valoracion WHERE id_documento = ? ORDER BY fecha DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearValoracion, id_documento);
    }

    private Valoracion mapearValoracion(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
        Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

        return new Valoracion(rs.getInt("id_valoracion"), (Cliente) usuario, documento, rs.getInt("puntuacion"), rs.getString("comentario"), rs.getDate("fecha_valoracion").toLocalDate());
    }
}
