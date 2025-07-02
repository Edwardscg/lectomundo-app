package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class AlquilerDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarAlquiler(Alquiler alquiler) {

        String sql = "INSERT INTO alquiler (id_usuario, id_documento, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";

        DBHelper.manejarEntidad(sql, alquiler.getCliente().getId_usuario(), alquiler.getDocumento().getId_documento(), Timestamp.valueOf(alquiler.getFecha_inicio()), Timestamp.valueOf(alquiler.getFecha_fin()), alquiler.getEstado_alquiler().name());
    }

    public void finalizarAlquiler(int id_alquiler) {

        String sql = "UPDATE alquiler SET estado = 'finalizado' WHERE id_alquiler = ?";
        DBHelper.manejarEntidad(sql, id_alquiler);
    }

    public List<Documento> verDocumentosAlquiladosPorCliente(int id_usuario) {

        String sql = " SELECT d.* FROM documento d JOIN alquiler a ON d.id_documento = a.id_documento WHERE a.id_usuario = ? AND a.estado = 'activo'";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    public List<Alquiler> obtenerAlquileresActivosPorCliente(int id_usuario) {

        String sql = " SELECT * From alquiler WHERE id_usuario = ? AND estado = 'activo';";

        return DBHelper.obtenerListaEntidad(sql, this::mapearAlquiler, id_usuario);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<Documento> llenarTablaDocumentosAlquiladosActivosPorUsuario(int id_usuario){

        String sql = "SELECT d.* FROM documento d JOIN alquiler a ON d.id_documento = a.id_documento WHERE a.id_usuario = ? AND a.estado = 'activo'";

        return DBHelper.llenarTablaPorParametro(sql, rs -> new Documento(rs.getInt("id_documento"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones")), id_usuario);
    }
    */

    public boolean estaAlquilado(int id_usuario, int id_documento) {

        String sql = "SELECT 1 FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo' LIMIT 1;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }

    public Alquiler obtenerAlquilerActivo(int id_usuario, int id_documento) {

        String sql = "SELECT * FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo' LIMIT 1";

        return DBHelper.obtenerEntidad(sql, this::mapearAlquiler, id_usuario, id_documento);
    }

    private Alquiler mapearAlquiler(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
            Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

            return new Alquiler(rs.getInt("id_alquiler"), (Cliente) usuario, documento, rs.getTimestamp("fecha_inicio").toLocalDateTime(), rs.getTimestamp("fecha_fin").toLocalDateTime(), Estado.valueOf(rs.getString("estado")));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos.");
        }
    }
}
