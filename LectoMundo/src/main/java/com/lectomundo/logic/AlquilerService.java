package com.lectomundo.logic;

import com.lectomundo.model.Alquiler;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Estado;
import com.lectomundo.repository.dao.AlquilerDAO;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class AlquilerService {

    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private final int dias_alquiler = 7;

    public void registrarAlquiler(Cliente cliente, Documento documento) {

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

    public void devolverDocumento(Cliente cliente, Documento documento) {

        Alquiler alquiler_activo = alquilerDAO.obtenerAlquilerActivo(cliente.getId_usuario(), documento.getId_documento());

        alquilerDAO.finalizarAlquiler(alquiler_activo.getId_alquiler());
    }

    public boolean estaAlquilado(int id_usuario, int id_documento) {

        return alquilerDAO.estaAlquilado(id_usuario, id_documento);
    }

    public List<Documento> obtenerDocumentosAlquiladosActivosPorUsuario(Cliente cliente) {

        return alquilerDAO.verDocumentosAlquiladosPorUsuario(cliente.getId_usuario());
    }

    public ObservableList<Documento> verDocumentosAlquiladosActivosPorUsuario(int id_usuario) {

        return alquilerDAO.llenarTablaDocumentosAlquiladosActivosPorUsuario(id_usuario);
    }
}
