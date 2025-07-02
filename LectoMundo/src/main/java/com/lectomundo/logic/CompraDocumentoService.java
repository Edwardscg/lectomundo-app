package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO = new CompraDocumentoDAO();
    UsuarioService usuarioService = new UsuarioService();
    NotificacionService notificacionService = new NotificacionService();
    AlquilerService alquilerService = new AlquilerService();
    MovimientoMonedaService movimientoMonedaService = new MovimientoMonedaService();

    public Cliente registrarCompra(Cliente cliente, Documento documento) {

        int costo = documento.getPrecio() * 3;

        LocalDateTime fecha_hoy = LocalDateTime.now();

        CompraDocumento compraDocumento = new CompraDocumento();
        compraDocumento.setCliente(cliente);
        compraDocumento.setDocumento(documento);
        compraDocumento.setFecha_compra(fecha_hoy);
        compraDocumento.setCosto(costo);

        compraDocumentoDAO.registrarCompra(compraDocumento);
        alquilerService.devolverDocumento(cliente, documento);
        notificacionService.notificacionCompraDocumento(cliente, documento);

        cliente = movimientoMonedaService.gastarMonedas(cliente, costo);

        return cliente;
    }

    public List<Documento> verDocumentosCompradosPorUsuario(Cliente cliente) {

        return compraDocumentoDAO.verDocumentosCompradosPorUsuario(cliente.getId_usuario());
    }


    /*

    FUTURA IMPLEMEMNTACIÓN

    public ObservableList<Documento> llenarTablaDocumentosCompradosPorUsuario(int id_usuario) {

        return compraDocumentoDAO.llenarTablaDocumentosCompradosPorUsuario(id_usuario);
    }
    */

    public boolean estaComprado(int id_usuario, int id_documento) {

        return compraDocumentoDAO.estaComprado(id_usuario, id_documento);
    }
}
