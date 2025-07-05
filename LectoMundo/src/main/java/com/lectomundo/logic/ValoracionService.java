package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Valoracion;
import com.lectomundo.repository.dao.DocumentoDAO;
import com.lectomundo.repository.dao.ValoracionDAO;

import java.util.List;

public class ValoracionService {

    private ValoracionDAO valoracionDAO = new ValoracionDAO();
    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarValoracion(Cliente cliente, Documento documento, String comentario, int puntuacion) {

        Valoracion valoracion = new Valoracion();
        valoracion.setCliente(cliente);
        valoracion.setDocumento(documento);
        valoracion.setComentario(comentario);
        valoracion.setPuntuacion(puntuacion);

        valoracionDAO.registrarValoracion(valoracion);

        documento.setPuntuacion_promedio(calcularNuevaPuntuacion(puntuacion, documento.getPuntuacion_promedio(), documento.getCantidad_valoraciones()));
        documento.setCantidad_valoraciones(documento.getCantidad_valoraciones() + 1);

        documentoDAO.actualizarPuntuación(documento);
    }

    public List<Valoracion> obtenerValoracionesPorDocumento(int id_documento) {

        return valoracionDAO.verValoracionesPorDocumento(id_documento);
    }

    private float calcularNuevaPuntuacion(int puntuacion, float puntuacion_promedio, int cantidad_valoraciones){

        float nueva_puntuacion = ((puntuacion_promedio * cantidad_valoraciones) + puntuacion) / (cantidad_valoraciones + 1);
        nueva_puntuacion = Math.round(nueva_puntuacion * 100f) / 100f;

        return nueva_puntuacion;
    }
}
