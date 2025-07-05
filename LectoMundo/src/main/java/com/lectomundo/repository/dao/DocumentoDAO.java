package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import javafx.collections.ObservableList;

import java.sql.*;

import java.util.List;

/**
 * Clase DAO encargada de realizar operaciones CRUD sobre la tabla "documento".
 * Incluye registros, ediciones, búsquedas y soporte para visualización en JavaFX.
 */
public class DocumentoDAO {

    /**
     * Inserta un nuevo documento en la base de datos.
     *
     * @param documento Documento a registrar.
     */
    public void registrarDocumento(Documento documento) {

        String sql = "INSERT INTO documento (titulo, autor, tipo_documento, fecha_publicacion, genero, descripcion, pdf_url, portada_url, precio, puntuacion_promedio, cantidad_valoraciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql,
                documento.getTitulo(),
                documento.getAutor(),
                documento.getTipo_documento(),
                Date.valueOf(documento.getFecha_publicacion()),
                documento.getGenero(),
                documento.getDescripcion(),
                documento.getPdf_url(),
                documento.getPortada_url(),
                documento.getPrecio(),
                documento.getPuntuacion_promedio(),
                documento.getCantidad_valoraciones());
    }

    /**
     * Actualiza los datos editables de un documento.
     *
     * @param documento Documento con los datos actualizados.
     */
    public void editarDocumento(Documento documento) {

        String sql = "UPDATE documento SET titulo = ?, autor = ?, tipo_documento = ?, fecha_publicacion = ?, genero = ?, descripcion = ?, precio = ? WHERE id_documento = ?;";

        DBHelper.manejarEntidad(sql,
                documento.getTitulo(),
                documento.getAutor(),
                documento.getTipo_documento(),
                Date.valueOf(documento.getFecha_publicacion()),
                documento.getGenero(),
                documento.getDescripcion(),
                documento.getPrecio(),
                documento.getId_documento());
    }

    /**
     * Actualiza la puntuación promedio y cantidad de valoraciones del documento.
     *
     * @param documento Documento con los valores actualizados.
     */
    public void actualizarPuntuación(Documento documento) {

        String sql = "UPDATE documento SET puntuacion_promedio = ?, cantidad_valoraciones = ? WHERE id_documento = ?;";

        DBHelper.manejarEntidad(sql,
                documento.getPuntuacion_promedio(),
                documento.getCantidad_valoraciones(),
                documento.getId_documento());
    }

    /**
     * Elimina un documento de la base de datos por su ID.
     *
     * @param id_documento ID del documento a eliminar.
     */
    public void eliminarDocumento(int id_documento) {

        String sql = "DELETE FROM documento WHERE id_documento = ?;";

        DBHelper.manejarEntidad(sql, id_documento);
    }

    /**
     * Busca un documento por su ID.
     *
     * @param id_documento ID del documento.
     * @return Documento encontrado o null si no existe.
     */
    public Documento buscarDocumentoPorId(int id_documento) {

        String sql = "SELECT * FROM documento WHERE id_documento = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearDocumento, id_documento);
    }

    /**
     * Obtiene todos los documentos en forma de lista de objetos Documento.
     *
     * @return Lista de documentos.
     */
    public List<Documento> obtenerDocumentos() {

        String sql = "SELECT * FROM documento;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento);
    }

    /**
     * Busca documentos cuyo título contenga el texto indicado.
     *
     * @param titulo Título parcial a buscar.
     * @return Lista de documentos coincidentes.
     */
    public List<Documento> buscarDocumentosPorTitulo(String titulo) {

        String sql = "SELECT * FROM documento WHERE titulo LIKE ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, "%" + titulo + "%");
    }

    /**
     * Busca documentos cuyo autor contenga el texto indicado.
     *
     * @param autor Nombre parcial del autor.
     * @return Lista de documentos coincidentes.
     */
    public List<Documento> buscarDocumentosPorAutor(String autor) {

        String sql = "SELECT * FROM documento WHERE autor LIKE ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, "%" + autor + "%");
    }

    /**
     * Busca documentos por tipo (e.g., libro, revista).
     *
     * @param tipo_documento Tipo de documento.
     * @return Lista de documentos del tipo indicado.
     */
    public List<Documento> buscarDocumentosPorTipo(String tipo_documento) {

        String sql = "SELECT * FROM documento WHERE tipo_documento LIKE ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, "%" + tipo_documento + "%");
    }

    /**
     * Busca documentos por género (e.g., fantasía, drama, ciencia ficción).
     *
     * @param genero Género del documento.
     * @return Lista de documentos del género indicado.
     */
    public List<Documento> buscarDocumentosPorGenero(String genero) {

        String sql = "SELECT * FROM documento WHERE genero LIKE ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, "%" + genero + "%");
    }

    /**
     * Devuelve todos los documentos registrados en la base de datos.
     * Se utiliza para llenar tablas en interfaces gráficas(TableView).
     *
     * @return Lista observable con documentos.
     */
    public ObservableList<Documento> verDocumentos() {

        String sql = "SELECT * FROM documento;";

        return DBHelper.llenarTabla(sql, rs -> new Documento(
                rs.getInt("id_documento"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("tipo_documento"),
                rs.getDate("fecha_publicacion").toLocalDate(),
                rs.getString("genero"),
                rs.getString("descripcion"),
                rs.getInt("precio"),
                rs.getFloat("puntuacion_promedio"),
                rs.getInt("cantidad_valoraciones")));
    }

    /**
     * Convierte una fila del ResultSet en un objeto Documento.
     *
     * @param rs Fila del resultado.
     * @return Objeto Documento.
     */
    public Documento mapearDocumento(ResultSet rs) {

        try {

            return new Documento(
                    rs.getInt("id_documento"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("tipo_documento"),
                    rs.getDate("fecha_publicacion").toLocalDate(),
                    rs.getString("genero"),
                    rs.getString("descripcion"),
                    rs.getString("pdf_url"),
                    rs.getString("portada_url"),
                    rs.getInt("precio"),
                    rs.getFloat("puntuacion_promedio"),
                    rs.getInt("cantidad_valoraciones"));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del documento desde la Base de Datos: " + e.getMessage());
        }
    }
}
