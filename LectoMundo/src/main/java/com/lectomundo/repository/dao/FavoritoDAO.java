package com.lectomundo.repository.dao;

import com.lectomundo.model.Documento;
import com.lectomundo.model.Favorito;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.util.List;

public class FavoritoDAO {

    DocumentoDAO documentoDAO = new DocumentoDAO();

    public void agregarFavorito(int id_usuario, int id_documento) throws Exception {

        if (existeregistro(id_usuario, id_documento)) {

            String sql = "UPDATE favorito SET es_favorito = 1 WHERE id_usuario = ? AND id_documento = ?";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        } else {

            String sql = "INSERT INTO favorito (id_usuario, id_documento) VALUES (?, ?)";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        }
    }

    public void eliminarFavorito(int id_usuario, int id_documento) throws Exception {

        if (esFavorito(id_usuario, id_documento)) {

            String sql = "UPDATE favorito SET es_favorito = 0 WHERE id_usuario = ? AND id_documento = ?";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        }
    }

    public List<Documento> obtenerFavoritosPorUsuario(int id_usuario) throws Exception {

        String sql = "SELECT d.* FROM favorito f JOIN documento d ON f.id_documento = d.id_documento WHERE f.id_usuario = ? AND f.es_favorito = 1";

        return DBHelper.obtenerListaEntidad(sql, documentoDAO::mapearDocumento, id_usuario);
    }

    private boolean existeregistro(int id_usuario, int id_documento) throws Exception{

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ? LIMIT 1";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }

    public boolean esFavorito(int id_usuario, int id_documento) throws Exception {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ? AND es_favorito = 1 LIMIT 1";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }

    // FUTURA AGREGACION DE METODO PARA OBTENER TODOS LOS REGISTROS E FAVORITO, Y MAPEAR FAVORITO
}
