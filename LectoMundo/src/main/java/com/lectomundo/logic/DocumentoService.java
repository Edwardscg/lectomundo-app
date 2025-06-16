package com.lectomundo.logic;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.dao.DocumentoDAO;
import javafx.collections.ObservableList;

import java.util.List;

public class DocumentoService {

    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarDocumento(Documento documento) throws Exception {

        validarDocumento(documento, false);
        documentoDAO.registrarDocumento(documento);
    }

    public void editarDocumento(Documento documento) throws Exception {

        validarDocumento(documento, true);
        documentoDAO.editarDocumento(documento);
    }

    public void eliminarDocumento(int id_documento) throws Exception {
        if (id_documento <= 0) {
            throw new IllegalArgumentException("ID de documento inválido.");
        }
        documentoDAO.eliminarDocumento(id_documento);
    }

    public Documento buscarDocumentoporId(int id_documento) throws Exception {
        if (id_documento <= 0) {
            throw new IllegalArgumentException("ID de documento inválido.");
        }
        return documentoDAO.buscarDocumentoPorId(id_documento);
    }

    public List<Documento> buscarDocumentosPorTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El campo título no puede estar vacío.");
        }
        return documentoDAO.buscarDocumentosPorTitulo(titulo);
    }

    public List<Documento> buscarDocumentosPorAutor(String autor) throws Exception {
        if (autor == null || autor.isEmpty()) {
            throw new IllegalArgumentException("El campo autor no puede estar vacío.");
        }
        return documentoDAO.buscarDocumentosPorAutor(autor);
    }

    public List<Documento> buscarDocumentoPorTipo(String tipo_documento) throws Exception{

        if(tipo_documento == null || tipo_documento.isBlank()){

            throw new IllegalArgumentException("El campo tipo de documento no puede estar vacio.");
        }

        return documentoDAO.buscarDocumentosPorTipo(tipo_documento);
    }

    public List<Documento> buscarDocumentosPorGenero(String genero) throws Exception {
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("El campo género no puede estar vacío.");
        }
        return documentoDAO.buscarDocumentosPorGenero(genero);
    }

    public List<Documento> traerDocumentos()throws Exception{

        return documentoDAO.obtenerDocumentos();
    }

    public ObservableList<Documento> verDocumentos()throws Exception{

        return documentoDAO.verDocumentos();
    }

    private void validarDocumento(Documento documento, boolean requiere_id) throws Exception {
        if (documento == null) {
            throw new IllegalArgumentException("El documento no puede ser nulo.");
        }
        if (requiere_id && documento.getId_documento() <= 0) {
            throw new IllegalArgumentException("ID de documento inválido.");
        }
        if (documento.getTitulo() == null || documento.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (documento.getAutor() == null || documento.getAutor().isBlank()) {
            throw new IllegalArgumentException("El autor es obligatorio.");
        }
        if (documento.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (documento.getPuntuacion_promedio() < 0.0 || documento.getPuntuacion_promedio() > 5.0) {
            throw new IllegalArgumentException("La puntuación debe estar entre 0 y 5.");
        }
        if (documento.getCantidad_valoraciones() < 0) {
            throw new IllegalArgumentException("La cantidad de valoraciones no puede ser negativa.");
        }
        if (documento.getFecha_publicacion() == null || documento.getFecha_publicacion().toString().isBlank()) {
            throw new IllegalArgumentException("La fecha de publicación es obligatoria.");
        }
    }
}
