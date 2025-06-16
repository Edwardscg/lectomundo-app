package com.lectomundo.logic;

import com.lectomundo.repository.dao.CompraDocumentoDAO;

public class CompraDocumentoService {

    CompraDocumentoDAO compraDocumentoDAO;

    public CompraDocumentoService() {
        compraDocumentoDAO = new CompraDocumentoDAO();
    }

    public boolean estaComprado(int id_usuario, int id_documento) throws Exception{

        return compraDocumentoDAO.estaComprado(id_usuario, id_documento);
    }
}
