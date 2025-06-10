package com.lectomundo.repository.dao;

import com.lectomundo.model.CompraDocumento;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Timestamp;

public class CompraDocumentoDAO {

    public void registrarCompra(CompraDocumento compra) throws Exception {

        String sql = "INSERT INTO compra_documento (id_usuario, id_documento, fecha_compra, costo) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, compra.getCliente().getId_usuario(), compra.getDocumento().getId_documento(), Timestamp.valueOf(compra.getFecha_compra()), compra.getCosto());
    }
}
