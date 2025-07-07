package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO = new CompraDocumentoDAO();
    NotificacionService notificacionService = new NotificacionService();
    AlquilerService alquilerService = new AlquilerService();
    MovimientoMonedaService movimientoMonedaService = new MovimientoMonedaService();

    public Cliente registrarCompra(Cliente cliente, Documento documento) {

        int costo = documento.getPrecio() * 3;

        CompraDocumento compraDocumento = new CompraDocumento();
        compraDocumento.setCliente(cliente);
        compraDocumento.setDocumento(documento);
        compraDocumento.setFecha_compra(LocalDateTime.now());
        compraDocumento.setCosto(costo);

        cliente = movimientoMonedaService.gastarMonedas(cliente, costo);

        if (cliente == null) {

            throw new RuntimeException("No cuenta con monedas suficientes.");
        }

        compraDocumentoDAO.registrarCompra(compraDocumento);
        notificacionService.notificacionCompraDocumento(cliente, documento);

        return cliente;
    }

    public List<Documento> verDocumentosCompradosPorCliente(Cliente cliente) {

        return compraDocumentoDAO.verDocumentosCompradosPorUsuario(cliente.getId_usuario());
    }


    /*

    FUTURA IMPLEMENTACIÓN

    public ObservableList<Documento> llenarTablaDocumentosCompradosPorUsuario(int id_usuario) {

        return compraDocumentoDAO.llenarTablaDocumentosCompradosPorUsuario(id_usuario);
    }
    */

    public boolean estaComprado(int id_cliente, int id_documento) {

        return compraDocumentoDAO.estaComprado(id_cliente, id_documento);
    }
}
