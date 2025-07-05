package com.lectomundo.repository.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de ayuda para ejecutar operaciones comunes en la base de datos,
 * como insertar, actualizar, eliminar o consultar datos.
 */

public class DBHelper {

    /**
     * Ejecuta una sentencia SQL (INSERT, UPDATE, DELETE) con parámetros.
     *
     * @param sql        Consulta SQL con parámetros ?
     * @param parametros Valores para reemplazar los ?
     * @return número de filas afectadas
     */
    public static int manejarEntidad(String sql, Object... parametros) {

        try (Connection conn = DBConexion.establecerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            asignarParametros(ps, parametros);

            return ps.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Error al ejecutar operación en la Base de Datos: " + e.getMessage());
        }
    }

    /**
     * Ejecuta una consulta SQL con parámetros y retorna el primer resultado mapeado.
     *
     * @param sql        Consulta SQL
     * @param mapper     Objeto que transforma cada fila del resultado
     * @param parametros Parámetros de la consulta
     * @param <T>        Tipo del objeto resultante
     * @return Objeto de tipo T o null si no hay resultados
     */
    public static <T> T obtenerEntidad(String sql, RowMapper<T> mapper, Object... parametros) {

        try (Connection conn = DBConexion.establecerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            asignarParametros(ps, parametros);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return mapper.mapRow(rs);
            }

            return null;

        } catch (Exception e) {

            throw new RuntimeException("Error al obtener entidad de la Base de Datos: " + e.getMessage());
        }
    }

    /**
     * Ejecuta una consulta SQL con parámetros y devuelve una lista de resultados.
     *
     * @param sql        Consulta SQL
     * @param mapper     Mapeador de filas
     * @param parametros Parámetros de la consulta
     * @param <T>        Tipo de la entidad
     * @return Lista de objetos mapeados
     */
    public static <T> List<T> obtenerListaEntidad(String sql, RowMapper<T> mapper, Object... parametros) {

        List<T> lista = new ArrayList<>();

        try (Connection conn = DBConexion.establecerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            asignarParametros(ps, parametros);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(mapper.mapRow(rs));
            }

            return lista;

        } catch (Exception e) {

            throw new RuntimeException("Error al obtener lista de entidades de la Base de Datos: " + e.getMessage());
        }
    }

    /**
     * Ejecuta una consulta SQL y devuelve los resultados como una lista observable (para TableView).
     *
     * @param sql        Consulta SQL
     * @param mapper     Mapeador de filas
     * @param parametros Parámetros opcionales de la consulta
     * @param <T>        Tipo de la entidad
     * @return ObservableList con los objetos mapeados
     */
    public static <T> ObservableList<T> llenarTabla(String sql, RowMapper<T> mapper, Object... parametros) {

        ObservableList<T> lista = FXCollections.observableArrayList();

        try (Connection conn = DBConexion.establecerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            asignarParametros(ps, parametros);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapper.mapRow(rs));
            }

            return lista;
        } catch (Exception e) {

            throw new RuntimeException("Error al llenar tabla desde la Base de Datos: " + e.getMessage());
        }
    }

    /**
     * Asigna los parámetros proporcionados a un PreparedStatement.
     *
     * @param ps         PreparedStatement al que se le asignarán los parámetros
     * @param parametros Parámetros a asignar
     * @throws SQLException si ocurre un error al asignar
     */
    private static void asignarParametros(PreparedStatement ps, Object... parametros) throws SQLException {

        for (int i = 0; i < parametros.length; i++) {

            Object obj = parametros[i];
            int indice = i + 1;

            if (obj == null) {
                ps.setNull(indice, Types.NULL);
            } else {
                ps.setObject(indice, obj);
            }
        }
    }
}
