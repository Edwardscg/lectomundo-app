package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO = new CompraDocumentoDAO();
    UsuarioService usuarioService = new UsuarioService();
    private NotificacionService notificacionService = new NotificacionService();

    public Cliente registrarCompra(Cliente cliente, Documento documento) {

        documento.setPrecio(documento.getPrecio() * 3);

        LocalDateTime fecha_hoy = LocalDateTime.now();

        CompraDocumento compraDocumento = new CompraDocumento();
        compraDocumento.setCliente(cliente);
        compraDocumento.setDocumento(documento);
        compraDocumento.setFecha_compra(fecha_hoy);
        compraDocumento.setCosto(documento.getPrecio());

        compraDocumentoDAO.registrarCompra(compraDocumento);
        notificacionService.notificacionCompraDocumento(cliente, documento);

        int nuevas_monedas = cliente.getMonedas() - documento.getPrecio();
        usuarioService.actualizarMonedas(cliente.getId_usuario(), nuevas_monedas);
        cliente.setMonedas(nuevas_monedas);

        return cliente;
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
