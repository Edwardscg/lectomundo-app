package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.MovimientoMoneda;
import com.lectomundo.repository.dao.MovimientoMonedaDAO;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class MovimientoMonedaService {

    private MovimientoMonedaDAO movimientoMonedaDAO = new MovimientoMonedaDAO();

    public void comprarMonedas(Cliente cliente, int monto)throws Exception{

        if (cliente == null || monto <= 0) {
            throw new IllegalArgumentException("Cliente inválido o monto no válido.");
        }

        cliente.setMonedas(cliente.getMonedas() + monto);

        MovimientoMoneda movimientoMoneda = new MovimientoMoneda();
        movimientoMoneda.setCliente(cliente);
        movimientoMoneda.setTipo_movimiento("compra");
        movimientoMoneda.setMonto(monto);

        movimientoMonedaDAO.registrarMovimiento(movimientoMoneda);
    }

    public void gastarMonedas(Cliente cliente, int monto) throws Exception{

        if (cliente == null || monto <= 0) {
            throw new IllegalArgumentException("Cliente inválido o monto no válido.");
        }

        if(cliente.getMonedas() < monto){

            return;
        }

        cliente.setMonedas(cliente.getMonedas() - monto);

        MovimientoMoneda movimientoMoneda = new MovimientoMoneda();
        movimientoMoneda.setCliente(cliente);
        movimientoMoneda.setTipo_movimiento("gasto");
        movimientoMoneda.setMonto(monto);

        movimientoMonedaDAO.registrarMovimiento(movimientoMoneda);
    }

    public ObservableList<MovimientoMoneda> obtenerMovimientos()throws Exception{

        return movimientoMonedaDAO.verMovimientos();
    }
}
