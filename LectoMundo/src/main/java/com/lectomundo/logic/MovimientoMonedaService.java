package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.MovimientoMoneda;
import com.lectomundo.repository.dao.MovimientoMonedaDAO;

import javafx.collections.ObservableList;

public class MovimientoMonedaService {

    private MovimientoMonedaDAO movimientoMonedaDAO = new MovimientoMonedaDAO();
    private UsuarioService usuarioService = new UsuarioService();

    public void comprarMonedas(Cliente cliente, int monto) {

        int nuevas_monedas = cliente.getMonedas() + monto;
        usuarioService.actualizarMonedas(cliente.getId_usuario(), nuevas_monedas);

        MovimientoMoneda movimientoMoneda = new MovimientoMoneda();
        movimientoMoneda.setCliente(cliente);
        movimientoMoneda.setTipo_movimiento("recarga");
        movimientoMoneda.setMonto(monto);

        movimientoMonedaDAO.registrarMovimiento(movimientoMoneda);
    }

    public void gastarMonedas(Cliente cliente, int monto) {

        if (cliente.getMonedas() < monto) {

            return;
        }

        int nuevas_monedas = cliente.getMonedas() - monto;
        usuarioService.actualizarMonedas(cliente.getId_usuario(), nuevas_monedas);

        MovimientoMoneda movimientoMoneda = new MovimientoMoneda();
        movimientoMoneda.setCliente(cliente);
        movimientoMoneda.setTipo_movimiento("gasto");
        movimientoMoneda.setMonto(monto);

        movimientoMonedaDAO.registrarMovimiento(movimientoMoneda);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public ObservableList<MovimientoMoneda> obtenerMovimientos() {

        return movimientoMonedaDAO.verMovimientos();
    }
    */
}
