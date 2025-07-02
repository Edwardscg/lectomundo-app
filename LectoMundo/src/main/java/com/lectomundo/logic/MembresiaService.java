package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Membresia;
import com.lectomundo.repository.dao.MembresiaDAO;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class MembresiaService {

    MembresiaDAO membresiaDAO = new MembresiaDAO();
    UsuarioService usuarioService = new UsuarioService();
    MovimientoMonedaService movimientoMonedaService = new MovimientoMonedaService();

    public Cliente registrarMembresia(Cliente cliente) {

        Membresia membresia = new Membresia();

        if (cliente.getMonedas() < membresia.getPrecio()) {
            return null;
        }

        cliente = movimientoMonedaService.gastarMonedas(cliente, membresia.getPrecio());

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        membresia.setCliente(cliente);
        membresia.setFecha_inicio(LocalDate.now());
        membresia.setFecha_fin(fechaFin);

        membresiaDAO.registrarMembresia(membresia);

        return cliente;
    }

    public Cliente actualizarMembresia(Cliente cliente) {

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        Membresia membresia = new Membresia();

        cliente = movimientoMonedaService.gastarMonedas(cliente, membresia.getPrecio());

        membresiaDAO.actualizarMembresia(cliente.getId_usuario(), fechaFin);

        return cliente;
    }

    public void finalizarMembresia(Cliente cliente) {

        membresiaDAO.finalizarMembresia(cliente.getId_usuario(), LocalDate.now());
    }

    public boolean tieneMembresiaActiva(int id_usuario) {

        return membresiaDAO.tieneMembresiaActiva(id_usuario);
    }

    public ObservableList<Membresia> verMembresias() {

        return membresiaDAO.verMembresias();
    }
}
