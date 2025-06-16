package com.lectomundo.logic;

import com.lectomundo.model.*;
import com.lectomundo.repository.dao.CompraDocumentoDAO;
import com.lectomundo.repository.dao.MembresiaDAO;

import java.time.LocalDateTime;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO;
    MembresiaDAO membresiaDAO;

    public CompraDocumentoService() {
        compraDocumentoDAO = new CompraDocumentoDAO();
        membresiaDAO = new MembresiaDAO();
    }

    public void comprarDocumento(Cliente cliente, Documento documento) throws Exception{

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

    public boolean estaComprado(int id_usuario, int id_documento) throws Exception{

        return compraDocumentoDAO.estaComprado(id_usuario, id_documento);
    }
}
