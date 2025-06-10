package com.lectomundo.repository.dao;

import com.lectomundo.repository.helper.DBHelper;

public class FavoritoDAO {

    public void agregarFavorito(int id_usuario, int id_documento) throws Exception {
        
        if (esFavorito(id_usuario, id_documento)) {

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

    public boolean esFavorito(int id_usuario, int id_documento) throws Exception {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ? LIMIT 1";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }
}
