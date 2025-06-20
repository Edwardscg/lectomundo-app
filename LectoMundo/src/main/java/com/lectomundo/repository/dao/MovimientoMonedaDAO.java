package com.lectomundo.repository.dao;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.MovimientoMoneda;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.helper.DBHelper;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class MovimientoMonedaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarMovimiento(MovimientoMoneda movimiviento) throws Exception {

        String sql = "INSERT INTO movimiento_moneda (id_usuario, tipo_movimiento, monto) VALUES (?, ?, ?);";

        DBHelper.manejarEntidad(sql, movimiviento.getCliente().getId_usuario(), movimiviento.getTipo_movimiento(), movimiviento.getMonto());
    }

    // POSIBLE BORRADO
    public ObservableList<MovimientoMoneda> verMovimientosPorUsuario(int id_usuario) throws Exception {

        String sql = "SELECT * FROM movimiento_moneda WHERE id_usuario = ? ORDER BY fecha DESC;";

        return DBHelper.llenarTabla(sql, this::mapearMovimiento);
    }

    // CAMBIAR A OBSERVABLE LIST
    public ObservableList<MovimientoMoneda> verMovimientos() throws Exception {

        String sql = "SELECT * FROM movimiento_moneda ORDER BY fecha DESC;";

        return DBHelper.llenarTabla(sql, this::mapearMovimiento);
    }

    private MovimientoMoneda mapearMovimiento(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

        return new MovimientoMoneda(rs.getInt("id_movimiento"), (Cliente) usuario, rs.getString("tipo_movimiento"), rs.getInt("monto"), rs.getTimestamp("fecha").toLocalDateTime());
    }
}
