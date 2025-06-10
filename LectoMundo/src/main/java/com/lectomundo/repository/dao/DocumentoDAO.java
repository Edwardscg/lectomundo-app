package com.lectomundo.repository.dao;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;

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
}
