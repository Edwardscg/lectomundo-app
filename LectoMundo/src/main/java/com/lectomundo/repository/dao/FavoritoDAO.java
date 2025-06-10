package com.lectomundo.repository.dao;

import com.lectomundo.repository.helper.DBHelper;

public class FavoritoDAO {

    public void agregarFavorito(int id_usuario, int id_documento) throws Exception {

        if(!esLibroFavorito(id_usuario, id_documento)){

            String sql = "INSERT INTO favorito (id_usuario, id_documento) VALUES (?, ?)";

            DBHelper.manejarEntidad(sql, id_usuario, id_documento);
        }
    }

    public boolean esLibroFavorito(int id_usuario, int id_documento) throws Exception {

        String sql = "SELECT 1 FROM favorito WHERE id_usuario = ? AND id_documento = ? LIMIT 1";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario, id_documento) != null;
    }
}
