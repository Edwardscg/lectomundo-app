package com.lectomundo.repository.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // Ejecuta una consulta SQL con parámetros y devuelve un objeto ConsultaResult
    // que encapsula la conexión, PreparedStatement y ResultSet asociados.
    public static  ConsultaManager ejecutarConsulta(String sql, Object... parametros) throws SQLException {

        Connection conn = DBConexion.establecerConexion();
        PreparedStatement ps = conn.prepareStatement(sql);

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

        ResultSet rs = ps.executeQuery();

        return new ConsultaManager(conn, ps, rs);
    }

    // Ejecuta una consulta SQL con parámetros, mapea la primera fila del resultado a un objeto T
    // mediante un RowMapper, y cierra automáticamente los recursos asociados.
    public static <T> T obtenerEntidad(String sql, RowMapper<T> mapper, Object... parametros) throws Exception{

        T entidad = null;
        ConsultaManager cr = null;

        try{

            cr = ejecutarConsulta(sql, parametros);
            ResultSet rs = cr.getResultSet();

            if(rs.next()){

                entidad = mapper.mapRow(rs);
            }

        }finally {

            if(cr!=null){

                cr.cerrar();
            }
        }
        return entidad;
    }

    // Ejecuta una consulta SQL con parámetros, mapea cada fila del ResultSet a un objeto de tipo T
    // usando un RowMapper, y devuelve una lista con todos los objetos mapeados.
    public static <T> List<T> obtenerListaEntidad(String sql, RowMapper<T> mapper, Object... parametros) throws Exception{

        List<T> lista = new ArrayList<>();

        ConsultaManager cr = null;

        try{

            cr = ejecutarConsulta(sql, parametros);
            ResultSet rs = cr.getResultSet();

            while(rs.next()){

                lista.add(mapper.mapRow(rs));
            }

        }finally {

            if(cr!=null){

                cr.cerrar();
            }
        }

        return lista;
    }

    // Ejecuta una consulta SQL y llena una ObservableList con objetos mapeados desde el ResultSet.
    public static <T> ObservableList<T> llenarTabla(String sql, RowMapper<T> mapper) {

        ObservableList<T> lista = FXCollections.observableArrayList();

        try (Connection conn = DBConexion.establecerConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                T objeto = mapper.mapRow(rs);
                lista.add(objeto);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return lista;
    }
}
