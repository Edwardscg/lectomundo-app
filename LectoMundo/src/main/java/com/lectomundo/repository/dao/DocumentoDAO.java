package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBConexion;
import com.lectomundo.repository.helper.DBHelper;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class DocumentoDAO {

    public void registrarDocumento(Documento documento) {

        String sql = "INSERT INTO documento (titulo, autor, tipo_documento, fecha_publicacion, genero, descripcion, pdf_url, portada_url, precio, puntuacion_promedio, cantidad_valoraciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, documento.getTitulo(), documento.getAutor(), documento.getTipo_documento(), Date.valueOf(documento.getFecha_publicacion()), documento.getGenero(), documento.getDescripcion(), documento.getPdf_url(), documento.getPortada_url(), documento.getPrecio(), documento.getPuntuacion_promedio(), documento.getCantidad_valoraciones());
    }

    public void editarDocumento(Documento documento) {

        String sql = "UPDATE documento SET titulo = ?, autor = ?, tipo_documento = ?, fecha_publicacion = ?, genero = ?, descripcion = ?, precio = ? WHERE id_documento = ?;";

        DBHelper.manejarEntidad(sql, documento.getTitulo(), documento.getAutor(), documento.getTipo_documento(), Date.valueOf(documento.getFecha_publicacion()), documento.getGenero(), documento.getDescripcion(), documento.getPrecio(), documento.getId_documento());
    }

    public void eliminarDocumento(int id_documento) {

        String sql = "DELETE FROM documento WHERE id_documento = ?;";

        DBHelper.manejarEntidad(sql, id_documento);
    }

    public Documento buscarDocumentoPorId(int id_documento) {

        String sql = "SELECT * FROM documento WHERE id_documento = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearDocumento, id_documento);
    }

    public List<Documento> obtenerDocumentos() {

        String sql = "select * from documento";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento);
    }

    public List<Documento> buscarDocumentosPorTitulo(String titulo) {

        String sql = "SELECT * FROM documento WHERE titulo LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, titulo);
    }

    public List<Documento> buscarDocumentosPorAutor(String autor) {

        String sql = "SELECT * FROM documento WHERE autor LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, autor);
    }

    public List<Documento> buscarDocumentosPorTipo(String tipo_documento) {

        String sql = "SELECT * FROM documento WHERE tipo_documento LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, tipo_documento);
    }

    public List<Documento> buscarDocumentosPorGenero(String genero) {

        String sql = "SELECT * FROM documento WHERE genero LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, genero);
    }

    public void actualizarPuntuacionYValoracion(int id_documento, float promedio, int cantidad_valoraciones) {

        String sql = "UPDATE documento SET puntuacion_promedio = ?, cantidad_valoraciones = ? WHERE id_documento = ?";

        DBHelper.manejarEntidad(sql, promedio, cantidad_valoraciones, id_documento);
    }

    public ObservableList<Documento> verDocumentos() {

        String sql = "SELECT * FROM documento";

        return DBHelper.llenarTabla(sql, rs -> new Documento(rs.getInt("id_documento"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones")));

    }

    public Documento mapearDocumento(ResultSet rs) {

        try {

            return new Documento(rs.getInt("id_documento"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getString("pdf_url"), rs.getString("portada_url"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones"));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del documento desde la Base de Datos.");
        }

    }
}
