package com.lectomundo.logic;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.dao.DocumentoDAO;

import javafx.collections.ObservableList;

import java.util.List;

public class DocumentoService {

    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarDocumento(Documento documento) {

        validarDocumento(documento, false);
        documentoDAO.registrarDocumento(documento);
    }

    public void editarDocumento(Documento documento) {

        validarDocumento(documento, true);
        documentoDAO.editarDocumento(documento);
    }

    public void eliminarDocumento(int id_documento) {

        documentoDAO.eliminarDocumento(id_documento);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public Documento buscarDocumentoporId(int id_documento) {

        return documentoDAO.buscarDocumentoPorId(id_documento);
    }
    */

    public List<Documento> buscarDocumentosPorTitulo(String titulo) {

        return documentoDAO.buscarDocumentosPorTitulo(titulo);
    }

    public List<Documento> buscarDocumentosPorAutor(String autor) {

        return documentoDAO.buscarDocumentosPorAutor(autor);
    }

    public List<Documento> buscarDocumentoPorTipo(String tipo_documento) {

        return documentoDAO.buscarDocumentosPorTipo(tipo_documento);
    }

    public List<Documento> buscarDocumentosPorGenero(String genero) {

        return documentoDAO.buscarDocumentosPorGenero(genero);
    }

    public List<Documento> traerDocumentos() {

        return documentoDAO.obtenerDocumentos();
    }

    public ObservableList<Documento> verDocumentos() {

        return documentoDAO.verDocumentos();
    }

    private void validarDocumento(Documento documento, boolean requiere_id) {

        if (requiere_id && documento.getId_documento() <= 0) {
            throw new RuntimeException("ID de documento inválido.");
        }
        if (documento.getTitulo() == null || documento.getTitulo().isBlank()) {
            throw new RuntimeException("El título es obligatorio.");
        }
        if (documento.getAutor() == null || documento.getAutor().isBlank()) {
            throw new RuntimeException("El autor es obligatorio.");
        }
        if (documento.getPrecio() < 0) {
            throw new RuntimeException("El precio no puede ser negativo.");
        }
        if (documento.getPuntuacion_promedio() < 0.0 || documento.getPuntuacion_promedio() > 5.0) {
            throw new RuntimeException("La puntuación debe estar entre 0 y 5.");
        }
        if (documento.getCantidad_valoraciones() < 0) {
            throw new RuntimeException("La cantidad de valoraciones no puede ser negativa.");
        }
        if (documento.getFecha_publicacion() == null || documento.getFecha_publicacion().toString().isBlank()) {
            throw new RuntimeException("La fecha de publicación es obligatoria.");
        }
    }
}
