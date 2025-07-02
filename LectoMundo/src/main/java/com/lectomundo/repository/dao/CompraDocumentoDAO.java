package com.lectomundo.repository.dao;

import com.lectomundo.model.CompraDocumento;
import com.lectomundo.model.Documento;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Timestamp;
import java.util.List;

public class CompraDocumentoDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarCompra(CompraDocumento compra) {

        String sql = "INSERT INTO compra_documento (id_usuario, id_documento, fecha_compra, costo) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, compra.getCliente().getId_usuario(), compra.getDocumento().getId_documento(), Timestamp.valueOf(compra.getFecha_compra()), compra.getCosto());
    }

    public boolean estaComprado(int id_usuario, int id_documento) {

        String sql = "SELECT 1 FROM compra_documento WHERE id_usuario = ? AND id_documento = ? LIMIT 1;";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }

    public List<Documento> verDocumentosCompradosPorUsuario(int id_usuario) {

        String sql = " SELECT d.* FROM documento d JOIN compra_documento cd ON d.id_documento = cd.id_documento WHERE cd.id_usuario = ?;";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<Documento> llenarTablaDocumentosCompradosPorUsuario(int id_usuario) {

        String sql = "SELECT d.* FROM documento d JOIN compra_documento cd ON d.id_documento = cd.id_documento WHERE cd.id_usuario = ?;";

        return DBHelper.llenarTablaPorParametro(sql, rs -> new Documento(rs.getInt("id_documento"), rs.getString("titulo"), rs.getString("autor"), rs.getString("tipo_documento"), rs.getDate("fecha_publicacion").toLocalDate(), rs.getString("genero"), rs.getString("descripcion"), rs.getInt("precio"), rs.getFloat("puntuacion_promedio"), rs.getInt("cantidad_valoraciones")), id_usuario);
    }
    */

    /*
    FUTURA IMPLEMENTACIÓN

    private CompraDocumento mapearCompra(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));
            Documento documento = documentoDAO.buscarDocumentoPorId((rs.getInt("id_documento")));

            return new CompraDocumento(rs.getInt("id_compra_libro"), (Cliente) usuario, documento, rs.getTimestamp("fecha_compra").toLocalDateTime(), rs.getInt("costo"));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de compra desde la Base de Datos.");
        }
    }
    */
}
