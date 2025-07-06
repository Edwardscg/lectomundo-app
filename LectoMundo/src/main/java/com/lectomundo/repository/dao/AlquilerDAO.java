package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

/**
 * Clase DAO encargada de gestionar las operaciones de persistencia relacionadas con la entidad Alquiler.
 * Permite registrar, finalizar, consultar y verificar alquileres activos de documentos por usuario.
 */
public class AlquilerDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    /**
     * Registra un nuevo alquiler en la base de datos.
     *
     * @param alquiler Objeto Alquiler a registrar.
     */
    public void registrarAlquiler(Alquiler alquiler) {

        String sql = "INSERT INTO alquiler (id_usuario, id_documento, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql,
                alquiler.getCliente().getId_usuario(),
                alquiler.getDocumento().getId_documento(),
                Timestamp.valueOf(alquiler.getFecha_inicio()),
                Timestamp.valueOf(alquiler.getFecha_fin()),
                alquiler.getEstado_alquiler().name());
    }

    /**
     * Marca un alquiler como finalizado.
     *
     * @param id_alquiler ID del alquiler a finalizar.
     */
    public void finalizarAlquiler(int id_alquiler) {

        String sql = "UPDATE alquiler SET estado = 'finalizado' WHERE id_alquiler = ?;";

        DBHelper.manejarEntidad(sql, id_alquiler);
    }

    /**
     * Obtiene la lista de documentos alquilados activamente por un cliente.
     *
     * @param id_cliente ID del cliente.
     * @return Lista de documentos alquilados.
     */
    public List<Documento> verDocumentosAlquiladosPorCliente(int id_cliente) {

        String sql = " SELECT d.* FROM documento d JOIN alquiler a ON d.id_documento = a.id_documento WHERE a.id_usuario = ? AND a.estado = 'activo';";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_cliente);
    }

    /**
     * Obtiene los alquileres activos asociados a un cliente.
     *
     * @param id_cliente ID del cliente.
     * @return Lista de alquileres activos.
     */
    public List<Alquiler> obtenerAlquileresActivosPorCliente(int id_cliente) {

        String sql = " SELECT * FROM alquiler WHERE id_usuario = ? AND estado = 'activo';";

        return DBHelper.obtenerListaEntidad(sql, this::mapearAlquiler, id_cliente);
    }

    /**
     * Verifica si un documento está alquilado activamente por un usuario.
     *
     * @param id_cliente   ID del usuario.
     * @param id_documento ID del documento.
     * @return true si el documento está alquilado, false si no.
     */
    public boolean estaAlquilado(int id_cliente, int id_documento) {

        String sql = "SELECT * FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo';";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_cliente, id_documento) != null;
    }

    /**
     * Obtiene el alquiler activo de un usuario sobre un documento específico.
     *
     * @param id_usuario   ID del usuario.
     * @param id_documento ID del documento.
     * @return Objeto Alquiler si existe, null si no.
     */
    public Alquiler obtenerAlquilerActivo(int id_usuario, int id_documento) {

        String sql = "SELECT id_alquiler, id_usuario, id_documento, fecha_inicio, fecha_fin, estado FROM alquiler WHERE id_usuario = ? AND id_documento = ? AND estado = 'activo';";

        return DBHelper.obtenerEntidad(sql, this::mapearAlquiler, id_usuario, id_documento);
    }

    /**
     * Convierte una fila de la tabla alquiler en un objeto Alquiler, accediendo a UsuarioDAO y DocumentoDAO.
     *
     * @param rs ResultSet con los datos de una fila.
     * @return Objeto Alquiler mapeado.
     */
    private Alquiler mapearAlquiler(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
            Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

            return new Alquiler(
                    rs.getInt("id_alquiler"),
                    (Cliente) usuario,
                    documento,
                    rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                    rs.getTimestamp("fecha_fin").toLocalDateTime(),
                    Estado.valueOf(rs.getString("estado")));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos: " + e.getMessage());
        }
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<Documento> llenarTablaDocumentosAlquiladosActivosPorUsuario(int id_usuario){

        String sql = "SELECT d.* FROM documento d JOIN alquiler a ON d.id_documento = a.id_documento WHERE a.id_usuario = ? AND a.estado = 'activo'";

        return DBHelper.llenarTablaPorParametro(sql, rs -> new Documento(rs.getInt("id_documento"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones")), id_usuario);
    }
    */
}
