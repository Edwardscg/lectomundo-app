package com.lectomundo.logic;

import com.lectomundo.model.Documento;
import com.lectomundo.repository.dao.DocumentoDAO;

import javafx.collections.ObservableList;

import java.util.List;

public class DocumentoService {

    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarDocumento(Documento documento) {

        documentoDAO.registrarDocumento(documento);
    }

    public void actualizarDocumento(Documento documento) {

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
}
