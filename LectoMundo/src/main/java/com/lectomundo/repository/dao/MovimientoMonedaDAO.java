package com.lectomundo.repository.dao;

import com.lectomundo.model.MovimientoMoneda;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.Timestamp;

public class MovimientoMonedaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarMovimiento(MovimientoMoneda movimiviento) throws Exception {

        String sql = "INSERT INTO movimiento_moneda (id_usuario, tipo_movimiento, monto, fecha) VALUES (?, ?, ?, ?);";

        DBHelper.manejarEntidad(sql, movimiviento.getCliente().getId_usuario(), movimiviento.getTipo_movimiento(), movimiviento.getMonto(), Timestamp.valueOf(movimiviento.getFecha_movimiento()));
    }

    
}
