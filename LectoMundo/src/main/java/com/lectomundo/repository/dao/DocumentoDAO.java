package com.lectomundo.repository.dao;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.helper.DBConexion;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.*;
import java.util.List;

public class DocumentoDAO {

    public void registrarDocumento(Documento documento) throws Exception {

        String sql = "INSERT INTO documento (titulo, autor, tipo_documento, fecha_publicacion, genero, descripcion, pdf_url, portada_url, precio, puntuacion_promedio, cantidad_valoraciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        int fila_afectada= DBHelper.manejarEntidad(sql, documento.getTitulo(), documento.getAutor(), documento.getTipo_documento(), Date.valueOf(documento.getFecha_publicacion()), documento.getGenero(), documento.getDescripcion(), documento.getPdf_url(), documento.getPortada_url(), documento.getPrecio(), documento.getPuntuacion_promedio(), documento.getCantidad_valoraciones());

        if (fila_afectada == 0){

            throw new Exception("No se pudo registrar el Documento");
        }
    }

    public void editarDocumento(Documento documento) throws Exception {

        String sql = "UPDATE documento SET titulo = ?, autor = ?, tipo_documento = ?, fecha_publicacion = ?, genero = ?, descripcion = ?, pdf_url = ?, portada_url = ?, precio = ?, puntuacion_promedio = ?, cantidad_valoraciones = ? WHERE id_documento = ?;";

        int fila_afectada= DBHelper.manejarEntidad(sql, documento.getTitulo(), documento.getAutor(), documento.getTipo_documento(), Date.valueOf(documento.getFecha_publicacion()), documento.getGenero(), documento.getDescripcion(), documento.getPdf_url(), documento.getPortada_url(), documento.getPrecio(), documento.getPuntuacion_promedio(), documento.getCantidad_valoraciones(), documento.getId_documento());

        if(fila_afectada==0){

            throw new Exception("No se pudo actualizar el Documento.");
        }
    }

    public void eliminarDocumento(int id_documento) throws Exception {

        String sql = "DELETE FROM documento WHERE id_documento = ?;";

        int fila_afectada = DBHelper.manejarEntidad(sql, id_documento);

        if(fila_afectada == 0){

            throw new Exception("No se pudo eliminar el Documento");
        }
    }

    public Documento buscarDocumentoPorId(int id_documento) throws Exception {

        String sql = "SELECT * FROM libro WHERE id_libro = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearDocumento, id_documento);
    }

    public List<Documento> obtenerDocumentos() throws Exception{

        String sql = "select * from documento";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento);
    }

    public List<Documento> buscarDocumentosPorTitulo(String titulo) throws Exception {

        String sql = "SELECT * FROM documento WHERE titulo LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, titulo);
    }

    public List<Documento> buscarDocumentosPorAutor(String autor) throws Exception {

        String sql = "SELECT * FROM documento WHERE autor LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, autor);
    }

    public List<Documento> buscarDocumentosPorTipo(String tipo_documento) throws Exception{

        String sql = "SELECT * FROM documento WHERE tipo_documento LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, tipo_documento);
    }

    public List<Documento> buscarDocumentosPorGenero(String genero) throws Exception {

        String sql = "SELECT * FROM documento WHERE genero LIKE ?";

        return DBHelper.obtenerListaEntidad(sql, this::mapearDocumento, genero);
    }

    public void actualizarPuntuacionYValoracion(int id_documento, float promedio, int cantidad_valoraciones) throws Exception {

        String sql = "UPDATE documento SET puntuacion_promedio = ?, cantidad_valoraciones = ? WHERE id_documento = ?";

        DBHelper.manejarEntidad(sql, promedio, cantidad_valoraciones, id_documento);
    }

    private Documento mapearDocumento(ResultSet rs) throws Exception{

        return new Documento(rs.getInt("id_libro"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getString("pdf_url"), rs.getString("portada_url"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones"));
    }
}
