package com.lectomundo.repository.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBHelper {

    // Ejecuta una sentencia SQL de tipo INSERT, UPDATE o DELETE con parámetros dinámicos.
    public static int manejarEntidad(String sql, Object... parametros) throws SQLException {

        try (Connection conn = DBConexion.establecerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {

                Object obj = parametros[i];
                int indice = i + 1;

                if (obj == null) {

                    ps.setNull(indice, Types.INTEGER);
                } else if (obj instanceof java.sql.Time) {

                    ps.setTime(indice, (java.sql.Time) obj);
                } else if (obj instanceof java.sql.Date) {

                    ps.setDate(indice, (java.sql.Date) obj);
                } else {

                    ps.setObject(indice, obj);
                }
            }

            return ps.executeUpdate();
        } catch (SQLException e) {

            System.out.println("Error en DBHelper.manejarEntidad(): " + e.getMessage());
            throw e;
        }
    }
}
