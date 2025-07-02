package com.lectomundo.repository.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Clase que encapsula los objetos Connection, PreparedStatement y ResultSet resultantes de una consulta SQL.
 * Proporciona acceso al ResultSet y un método para cerrar correctamente todos los recursos asociados,
 * asegurando la liberación adecuada y evitando fugas de recursos en la base de datos.
 */
public class ConsultaManager {
    private final Connection conn;
    private final PreparedStatement ps;
    private final ResultSet rs;

    public ConsultaManager(Connection conn, PreparedStatement ps, ResultSet rs) {
        this.conn = conn;
        this.ps = ps;
        this.rs = rs;
    }

    public ResultSet getResultSet() {

        return rs;
    }

    public void cerrar() {

        try {

            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
