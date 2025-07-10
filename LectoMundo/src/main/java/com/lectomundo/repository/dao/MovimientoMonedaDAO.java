package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

/**
 * DAO encargado de gestionar los movimientos de monedas realizados por los usuarios.
 * Permite registrar nuevos movimientos y consultar el historial completo de movimientos.
 */
public class MovimientoMonedaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Registra un nuevo movimiento de monedas para un cliente.
     *
     * @param movimiento Objeto MovimientoMoneda que contiene los datos a insertar.
     */
    public void registrarMovimiento(MovimientoMoneda movimiento) {

        String sql = "INSERT INTO movimiento_moneda (id_usuario, tipo_movimiento, monto) VALUES (?, ?, ?);";

        DBHelper.manejarEntidad(sql, movimiento.getCliente().getId_usuario(), movimiento.getTipo_movimiento(), movimiento.getMonto());
    }

    /*
    FUTURA IMPLEMENTACIÓN

    private MovimientoMoneda mapearMovimiento(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new MovimientoMoneda(rs.getInt("id_movimiento"), (Cliente) usuario, rs.getString("tipo_movimiento"), rs.getInt("monto"), rs.getTimestamp("fecha").toLocalDateTime());

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de movimiento de monedas desde la Base de Datos.");
        }
    }
    */

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<MovimientoMoneda> verMovimientosPorUsuario(int id_usuario) {

        String sql = "SELECT * FROM movimiento_moneda WHERE id_usuario = ? ORDER BY fecha DESC;";

        return DBHelper.llenarTablaPorParametro(sql, this::mapearMovimiento, id_usuario);
    }
    */

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<MovimientoMoneda> verMovimientos() {

        String sql = "SELECT * FROM movimiento_moneda ORDER BY fecha DESC;";

        return DBHelper.llenarTabla(sql, this::mapearMovimiento);
    }
    */
}
