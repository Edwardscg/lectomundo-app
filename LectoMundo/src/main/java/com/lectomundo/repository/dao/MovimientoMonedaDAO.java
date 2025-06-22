package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class MovimientoMonedaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarMovimiento(MovimientoMoneda movimiviento) {

        String sql = "INSERT INTO movimiento_moneda (id_usuario, tipo_movimiento, monto) VALUES (?, ?, ?);";

        DBHelper.manejarEntidad(sql, movimiviento.getCliente().getId_usuario(), movimiviento.getTipo_movimiento(), movimiviento.getMonto());
    }

    // POSIBLE BORRADO
    public ObservableList<MovimientoMoneda> verMovimientosPorUsuario(int id_usuario) {

        String sql = "SELECT * FROM movimiento_moneda WHERE id_usuario = ? ORDER BY fecha DESC;";

        return DBHelper.llenarTablaPorParametro(sql, this::mapearMovimiento, id_usuario);
    }

    public ObservableList<MovimientoMoneda> verMovimientos() {

        String sql = "SELECT * FROM movimiento_moneda ORDER BY fecha DESC;";

        return DBHelper.llenarTabla(sql, this::mapearMovimiento);
    }

    private MovimientoMoneda mapearMovimiento(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new MovimientoMoneda(rs.getInt("id_movimiento"), (Cliente) usuario, rs.getString("tipo_movimiento"), rs.getInt("monto"), rs.getTimestamp("fecha").toLocalDateTime());

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de movimiento de monedas desde la Base de Datos.");
        }
    }
}
