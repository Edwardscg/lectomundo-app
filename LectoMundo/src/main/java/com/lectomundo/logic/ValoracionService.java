package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Valoracion;
import com.lectomundo.repository.dao.ValoracionDAO;

import java.util.List;

public class ValoracionService {

    private ValoracionDAO valoracionDAO = new ValoracionDAO();

    public void registrarValoracion(Cliente cliente, Documento documento, String comentario, int puntuacion) {

        Valoracion valoracion = new Valoracion();
        valoracion.setCliente(cliente);
        valoracion.setDocumento(documento);
        valoracion.setComentario(comentario);
        valoracion.setPuntuacion(puntuacion);

        valoracionDAO.registrarValoracion(valoracion);
    }

    public List<Valoracion> verValoracionesPorDocumento(int id_documento) {

        return valoracionDAO.verValoracionesPorDocumento(id_documento);
    }

    // FALTA IMPLEMENTAR

    public float obtenerPromedioValoracion(int id_documento) {

        return valoracionDAO.obtenerPromedioValoracion(id_documento);
    }

    public int contarValoraciones(int id_documento) {

        return valoracionDAO.contarValoracionesPorDocumento(id_documento);
    }
}
