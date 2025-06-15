package com.lectomundo.logic;

import com.lectomundo.model.Alquiler;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Estado;
import com.lectomundo.repository.dao.AlquilerDAO;
import com.lectomundo.repository.dao.MembresiaDAO;

import java.time.LocalDateTime;
import java.util.List;

public class AlquilerService {

    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private MembresiaDAO membresiaDAO = new MembresiaDAO();
    private final int dias_alquiler = 7;

    public void alquilarDocumento(Cliente cliente, Documento documento) throws Exception{

        if (cliente == null || documento == null) {
            throw new IllegalArgumentException("Cliente o documento no válido.");
        }

        if (membresiaDAO.tieneMembresiaActiva(cliente.getId_usuario())) {
            throw new IllegalStateException("Con membresía activa, no se requiere alquilar.");
        }

        if (alquilerDAO.estaAlquilado(cliente.getId_usuario(), documento.getId_documento())) {
            throw new IllegalStateException("El documento ya está alquilado por este cliente.");
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusDays(dias_alquiler);

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(cliente);
        alquiler.setDocumento(documento);
        alquiler.setFecha_inicio(inicio);
        alquiler.setFecha_fin(fin);
        alquiler.setEstado_alquiler(Estado.activo);

        alquilerDAO.registrarAlquiler(alquiler);
    }

    public void devolverDocumento(Cliente cliente, Documento documento) throws Exception{

        Alquiler alquiler_activo = alquilerDAO.obtenerAlquilerActivo(cliente.getId_usuario(), documento.getId_documento());

        alquilerDAO.finalizarAlquiler(alquiler_activo.getId_alquiler());
    }

    public boolean estaAlquilado(int id_usuario, int id_documento) throws Exception{

        return alquilerDAO.estaAlquilado(id_usuario, id_documento);
    }

    public List<Documento> obtenerAlquileresActivosPorUsuario(Cliente cliente) throws Exception{

        return alquilerDAO.verDocumentosAlquiladosPorUsuario(cliente.getId_usuario());
    }


}
