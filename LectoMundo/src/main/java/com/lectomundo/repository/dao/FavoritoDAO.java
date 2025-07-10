package com.lectomundo.repository.dao;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.helper.DBHelper;

import java.util.List;

/**
 * DAO encargado de gestionar documentos favoritos de los clientes.
 * Permite agregar, eliminar, consultar y verificar favoritos.
 */
public class FavoritoDAO {

    DocumentoDAO documentoDAO = new DocumentoDAO();

    /**
     * Marca un documento como favorito para un cliente.
     * Si ya existe el registro, actualiza el campo es_favorito a 1.
     * Si no existe, lo inserta como nuevo.
     *
     * @param id_usuario   ID del cliente.
     * @param id_documento ID del documento.
     */
    public void agregarFavorito(int id_usuario, int id_documento) {

        if (existeRegistro(id_usuario, id_documento)) {

            String sql = "UPDATE favorito SET es_favorito = 1 WHERE id_usuario = ? AND id_documento = ?;";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        } else {

            String sql = "INSERT INTO favorito (id_usuario, id_documento) VALUES (?, ?);";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        }
    }

    /**
     * Desmarca un documento como favorito para un cliente (sin eliminar el registro).
     *
     * @param id_usuario   ID del cliente.
     * @param id_documento ID del documento.
     */
    public void eliminarFavorito(int id_usuario, int id_documento) {

        if (esFavorito(id_usuario, id_documento)) {

            String sql = "UPDATE favorito SET es_favorito = 0 WHERE id_usuario = ? AND id_documento = ?;";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        }
    }

    /**
     * Obtiene la lista de documentos marcados como favoritos por un cliente.
     *
     * @param id_usuario ID del cliente.
     * @return Lista de documentos favoritos.
     */
    public List<Documento> obtenerFavoritosPorUsuario(int id_usuario) {

        String sql = "SELECT d.* FROM favorito f JOIN documento d ON f.id_documento = d.id_documento WHERE f.id_usuario = ? AND f.es_favorito = 1;";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    /**
     * Verifica si existe algún registro de favorito (activo o inactivo) entre un cliente y un documento.
     *
     * @param id_usuario   ID del cliente.
     * @param id_documento ID del documento.
     * @return true si existe el registro, false si no.
     */
    private boolean existeRegistro(int id_usuario, int id_documento) {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ?;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }

    /**
     * Verifica si un documento está marcado como favorito por un cliente.
     *
     * @param id_usuario   ID del cliente.
     * @param id_documento ID del documento.
     * @return true si es favorito, false si no.
     */
    public boolean esFavorito(int id_usuario, int id_documento) {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ? AND es_favorito = 1;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }
}
