package com.lectomundo.repository.dao;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.CompraDocumento;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class CompraDocumentoDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarCompra(CompraDocumento compra) throws Exception {

        String sql = "INSERT INTO compra_documento (id_usuario, id_documento, fecha_compra, costo) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, compra.getCliente().getId_usuario(), compra.getDocumento().getId_documento(), Timestamp.valueOf(compra.getFecha_compra()), compra.getCosto());
    }

    public boolean estaComprado(int id_usuario, int id_documento) throws Exception {

        String sql = "SELECT 1 FROM compra_documento WHERE id_usuario = ? AND id_documento = ? LIMIT 1;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) !=null;
    }

    public List<Documento> verDocumentosCompradosPorUsuario(int id_usuario) throws Exception{

        String sql = " SELECT l.* FROM documento d JOIN compra_documento cd ON d.id_documento = cd.id_documento WHERE c.id_usuario = ? ";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    public List<CompraDocumento> verComprasPorUsuario(int id_usuario) throws Exception {

        String sql = "SELECT * FROM compra_documento WHERE id_usuario = ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearCompra, id_usuario);
    }

    public List<CompraDocumento> verCompras() throws Exception {

        String sql = "SELECT * FROM compra_libro;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearCompra);
    }

    private CompraDocumento mapearCompra(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
        Documento documento = documentoDAO.buscarDocumentoPorId((rs.getInt("id_documento")));

        return new CompraDocumento(rs.getInt("id_compra_libro"), (Cliente) usuario, documento, rs.getTimestamp("fecha_compra").toLocalDateTime(), rs.getInt("costo"));
    }

    // Agregar metodo observablelist para llenarTabla
}
