package com.lectomundo.logic;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.dao.DocumentoDAO;

public class DocumentoService {

    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarDocumento(Documento documento) throws Exception {

        validarDocumento(documento, false);
        documentoDAO.registrarDocumento(documento);
    }

    public void editarDocumento(Documento documento) throws Exception {

        validarDocumento(documento, true);
        documentoDAO.editarDocumento(documento);
    }

    private void validarDocumento(Documento documento, boolean requiere_id) throws Exception {
        if (documento == null) {
            throw new IllegalArgumentException("El documento no puede ser nulo.");
        }
        if (requiere_id && documento.getId_documento() <= 0) {
            throw new IllegalArgumentException("ID de documento inválido.");
        }
        if (documento.getTitulo() == null || documento.getTitulo().trim().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (documento.getAutor() == null || documento.getAutor().trim().isBlank()) {
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
