package com.lectomundo.logic;

import com.lectomundo.model.Estado;
import com.lectomundo.model.Membresia;
import com.lectomundo.repository.dao.MembresiaDAO;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class MembresiaService {

    MembresiaDAO membresiaDAO = new MembresiaDAO();

    public void registrarMembresia(Membresia membresia) throws Exception{

        validarMembresia(membresia);

        membresiaDAO.registrarMembresia(membresia);
    }

    public void actualizarMembresia (Membresia membresia) throws Exception{

        validarMembresia(membresia);

        membresiaDAO.actualizarMembresia(membresia);
    }

    public void finalizarMembresia(int id_membresia) throws Exception{

        if(id_membresia <=0){

            throw new IllegalArgumentException("ID de membresía inválido");
        }

        membresiaDAO.finalizarMembresia(id_membresia);
    }

    public ObservableList<Membresia> verMembresias() throws Exception{

        return membresiaDAO.verMembresias();
    }

    private void validarMembresia(Membresia membresia) throws Exception{

        if (membresia == null) {
            throw new IllegalArgumentException("La membresía no puede ser nula.");
        }

        if (membresia.getCliente().getId_usuario() <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }

        if (membresia.getFecha_fin() == null || membresia.getFecha_fin().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a hoy.");
        }

        if (membresia.getPrecio() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }

        Estado estado = membresia.getEstado_membresia();
        if ((estado != Estado.activo && estado != Estado.finalizado)) {
            throw new IllegalArgumentException("Estado inválido. Debe ser 'activo' o 'finalizado'.");
        }
    }
}
