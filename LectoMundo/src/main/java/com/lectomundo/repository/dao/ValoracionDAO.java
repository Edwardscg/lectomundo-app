package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.util.List;

/**
 * DAO que gestiona las operaciones relacionadas con las valoraciones de documentos por parte de los usuarios.
 * Permite registrar una nueva valoración y consultar valoraciones por documento.
 */
public class ValoracionDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    /**
     * Registra una nueva valoración hecha por un cliente sobre un documento.
     *
     * @param valoracion Objeto Valoracion con los datos de puntuación, comentario y referencias.
     */
    public void registrarValoracion(Valoracion valoracion) {

        String sql = "INSERT INTO valoracion (id_usuario, id_documento, puntuacion, comentario) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql,
                valoracion.getCliente().getId_usuario(),
                valoracion.getDocumento().getId_documento(),
                valoracion.getPuntuacion(),
                valoracion.getComentario());
    }

    /**
     * Obtiene todas las valoraciones asociadas a un documento específico, ordenadas por fecha descendente.
     *
     * @param id_documento ID del documento del cual se desean ver las valoraciones.
     * @return Lista de valoraciones realizadas al documento.
     */
    public List<Valoracion> verValoracionesPorDocumento(int id_documento) {

        String sql = "SELECT * FROM valoracion WHERE id_documento = ? ORDER BY fecha_valoracion DESC;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearValoracion, id_documento);
    }

    /**
     * Mapea una fila del ResultSet a un objeto Valoracion.
     *
     * @param rs ResultSet proveniente de una consulta SQL.
     * @return Objeto Valoracion con los datos del ResultSet.
     */
    private Valoracion mapearValoracion(ResultSet rs) {

        try {

            Cliente cliente = (Cliente) usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
            Documento documento = documentoDAO.buscarDocumentoPorId(rs.getInt("id_documento"));

            return new Valoracion(rs.getInt("id_valoracion"),
                    cliente,
                    documento,
                    rs.getInt("puntuacion"),
                    rs.getString("comentario"),
                    rs.getDate("fecha_valoracion").toLocalDate());

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos.");
        }
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
}
