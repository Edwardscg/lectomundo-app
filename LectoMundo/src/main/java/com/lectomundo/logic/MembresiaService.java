package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Membresia;
import com.lectomundo.repository.dao.MembresiaDAO;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class MembresiaService {

    MembresiaDAO membresiaDAO = new MembresiaDAO();

    // CAMBIAR A QUE RECIBA CLIENTE COMO ARGUMENTO
    public void registrarMembresia(Cliente cliente) throws Exception{

        if(membresiaDAO.tieneMembresiaActiva(cliente.getId_usuario())){

            throw new IllegalArgumentException("Ya cuentas con una membresía activa.");
        }

        Membresia membresia = new Membresia();

        if(cliente.getMonedas()<membresia.getPrecio()){

            return;
        }

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        membresia.setCliente(cliente);
        membresia.setFecha_inicio(LocalDate.now());
        membresia.setFecha_fin(fechaFin);

        membresiaDAO.registrarMembresia(membresia);
    }

    public void actualizarMembresia (Cliente cliente) throws Exception{

        LocalDate fechaFin = LocalDate.now().plusDays(30);
        Membresia membresia = new Membresia();
        membresia.setFecha_fin(fechaFin);

        membresiaDAO.actualizarMembresia(membresia);
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
