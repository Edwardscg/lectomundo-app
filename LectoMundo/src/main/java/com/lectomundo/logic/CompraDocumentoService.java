package com.lectomundo.logic;

public class CompraDocumentoService {

    CompraDocumentoService compraDocumentoService = new CompraDocumentoService();

    public boolean estaComprado(int id_usuario, int id_documento) throws Exception{

        return compraDocumentoService.estaComprado(id_usuario, id_documento);
    }
}
