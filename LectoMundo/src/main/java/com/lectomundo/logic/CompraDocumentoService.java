package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;
import com.lectomundo.repository.dao.MembresiaDAO;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO = new CompraDocumentoDAO();
    MembresiaDAO membresiaDAO = new MembresiaDAO();

    public void registrarCompra(Cliente cliente, Documento documento) throws Exception{

        if (cliente == null || documento == null) {
            throw new IllegalArgumentException("Cliente o documento no válido.");
        }

        if (membresiaDAO.tieneMembresiaActiva(cliente.getId_usuario())) {
            throw new IllegalStateException("Con membresía activa, no se requiere comprar.");
        }

        if(compraDocumentoDAO.estaComprado(cliente.getId_usuario(), documento.getId_documento())){

            throw new IllegalStateException("El documento ya está comprado.");
        }

        LocalDateTime fecha_hoy = LocalDateTime.now();

        CompraDocumento compraDocumento = new CompraDocumento();
        compraDocumento.setCliente(cliente);
        compraDocumento.setDocumento(documento);
        compraDocumento.setFecha_compra(fecha_hoy);
        compraDocumento.setCosto(documento.getPrecio()*3);

        compraDocumentoDAO.registrarCompra(compraDocumento);
    }

    public List<Documento> verDocumentosCompradosPorUsuario(Cliente cliente) throws Exception{

        return compraDocumentoDAO.verDocumentosCompradosPorUsuario(cliente.getId_usuario());
    }

    public boolean estaComprado(int id_usuario, int id_documento) throws Exception{

        return compraDocumentoDAO.estaComprado(id_usuario, id_documento);
    }
}
