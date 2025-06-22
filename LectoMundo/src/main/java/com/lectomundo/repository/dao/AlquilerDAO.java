package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarAlquiler(Alquiler alquiler) {

        try {

            String sql = "INSERT INTO alquiler (id_usuario, id_documento, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";

            DBHelper.manejarEntidad(sql, alquiler.getCliente().getId_usuario(), alquiler.getDocumento().getId_documento(), Timestamp.valueOf(alquiler.getFecha_inicio()), Timestamp.valueOf(alquiler.getFecha_fin()), alquiler.getEstado_alquiler().name());

        }catch (RuntimeException e){

            throw new RuntimeException("No se pudo registrar el alquiler.");
        }
    }

    public void finalizarAlquiler(int id_alquiler) throws Exception {
        String sql = "UPDATE alquiler SET estado = 'finalizado' WHERE id_alquiler = ?";
        DBHelper.manejarEntidad(sql, id_alquiler);
    }

    // CAMBIAR POR OBSERBABLELIST DE ALQUILERS POR USUARIO
    public Alquiler buscarAlquilerPorId(int id_alquiler) throws Exception {

        String sql = "SELECT * FROM alquiler WHERE id_alquiler = ?";

        return DBHelper.obtenerEntidad(sql, this::mapearAlquiler, id_alquiler);
    }

    public List<Documento> verDocumentosAlquiladosPorUsuario(int id_usuario) throws Exception{

        String sql = " SELECT d.* FROM documento d JOIN alquiler a ON d.id_documento = a.id_documento WHERE a.id_usuario = ? AND a.estado = 'activo'";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    public boolean estaAlquilado(int id_usuario, int id_documento) throws Exception {

        String sql = "SELECT 1 FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo' LIMIT 1;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) !=null;
    }

    public Alquiler obtenerAlquilerActivo(int id_usuario, int id_documento) throws Exception{

        String sql = "SELECT * FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo' LIMIT 1";

        return DBHelper.obtenerEntidad(sql, this::mapearAlquiler, id_usuario, id_documento);
    }

    private Alquiler mapearAlquiler(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
        Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

        return new Alquiler(rs.getInt("id_alquiler"), (Cliente) usuario, documento, rs.getTimestamp("fecha_inicio").toLocalDateTime(), rs.getTimestamp("fecha_fin").toLocalDateTime(), Estado.valueOf(rs.getString("estado")));
    }
}
