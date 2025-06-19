package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Membresia;
import com.lectomundo.repository.dao.MembresiaDAO;
import com.lectomundo.repository.dao.UsuarioDAO;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class MembresiaService {

    MembresiaDAO membresiaDAO = new MembresiaDAO();
    UsuarioService usuarioService = new UsuarioService();

    // CAMBIAR A QUE RECIBA CLIENTE COMO ARGUMENTO
    public Cliente registrarMembresia(Cliente cliente) throws Exception{

        if(membresiaDAO.tieneMembresiaActiva(cliente.getId_usuario())){

            throw new IllegalArgumentException("Ya cuentas con una membresía activa.");
        }

        Membresia membresia = new Membresia();

        if(cliente.getMonedas()<membresia.getPrecio()) {
            return null;
        }

        int nuevas_monedas = cliente.getMonedas() - membresia.getPrecio();
        usuarioService.actualizarMonedas(cliente.getId_usuario(), nuevas_monedas);
        cliente.setMonedas(nuevas_monedas);

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        membresia.setCliente(cliente);
        membresia.setFecha_inicio(LocalDate.now());
        membresia.setFecha_fin(fechaFin);

        membresiaDAO.registrarMembresia(membresia);

        return cliente;
    }

    public Cliente actualizarMembresia (Cliente cliente) throws Exception{

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        Membresia membresia = new Membresia();

        int nuevas_monedas = cliente.getMonedas() - membresia.getPrecio();
        usuarioService.actualizarMonedas(cliente.getId_usuario(), nuevas_monedas);
        cliente.setMonedas(nuevas_monedas);

        membresiaDAO.actualizarMembresia(cliente.getId_usuario(), fechaFin);

        return cliente;
    }

    public void finalizarMembresia(Cliente cliente) throws Exception{

        membresiaDAO.finalizarMembresia(cliente.getId_usuario(), LocalDate.now());
    }

    public boolean tieneMembresiaActiva(int id_usuario) throws Exception{

        return membresiaDAO.tieneMembresiaActiva(id_usuario);
    }

    public ObservableList<Membresia> verMembresias() throws Exception{

        return membresiaDAO.verMembresias();
    }
}
