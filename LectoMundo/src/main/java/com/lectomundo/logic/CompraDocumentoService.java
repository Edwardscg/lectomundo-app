package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO = new CompraDocumentoDAO();
    private NotificacionService notificacionService = new NotificacionService();

    public void registrarCompra(Cliente cliente, Documento documento) {

        LocalDateTime fecha_hoy = LocalDateTime.now();

        CompraDocumento compraDocumento = new CompraDocumento();
        compraDocumento.setCliente(cliente);
        compraDocumento.setDocumento(documento);
        compraDocumento.setFecha_compra(fecha_hoy);
        compraDocumento.setCosto(documento.getPrecio() * 3);

        compraDocumentoDAO.registrarCompra(compraDocumento);
        notificacionService.notificacionCompraDocumento(cliente, documento);
    }

    public List<Documento> verDocumentosCompradosPorUsuario(Cliente cliente) {

        return compraDocumentoDAO.verDocumentosCompradosPorUsuario(cliente.getId_usuario());
    }

    public ObservableList<Documento> llenarTablaDocumentosCompradosPorUsuario(int id_usuario) {

        return compraDocumentoDAO.llenarTablaDocumentosCompradosPorUsuario(id_usuario);
    }

    public boolean estaComprado(int id_usuario, int id_documento) {

        return compraDocumentoDAO.estaComprado(id_usuario, id_documento);
    }
}
