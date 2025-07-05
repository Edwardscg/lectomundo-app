package com.lectomundo.logic;

import com.lectomundo.model.Alquiler;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Estado;
import com.lectomundo.repository.dao.AlquilerDAO;

import java.time.LocalDateTime;
import java.util.List;

public class AlquilerService {

    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private NotificacionService notificacionService = new NotificacionService();
    private MovimientoMonedaService movimientoMonedaService = new MovimientoMonedaService();
    private final int dias_alquiler = 7;

    public Cliente registrarAlquiler(Cliente cliente, Documento documento) {

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusDays(dias_alquiler);

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(cliente);
        alquiler.setDocumento(documento);
        alquiler.setFecha_inicio(inicio);
        alquiler.setFecha_fin(fin);
        alquiler.setEstado_alquiler(Estado.activo);

        cliente = movimientoMonedaService.gastarMonedas(cliente, documento.getPrecio());
        if(cliente == null){

            throw new RuntimeException("No cuenta con monedas suficientes.");
        }

        alquilerDAO.registrarAlquiler(alquiler);
        notificacionService.notificacionAlquilerDocumento(cliente, documento);

        return cliente;
    }

    public void devolverDocumento(Cliente cliente, Documento documento) {

        Alquiler alquiler_activo = alquilerDAO.obtenerAlquilerActivo(cliente.getId_usuario(), documento.getId_documento());

        alquilerDAO.finalizarAlquiler(alquiler_activo.getId_alquiler());
    }

    public boolean estaAlquilado(int id_cliente, int id_documento) {

        return alquilerDAO.estaAlquilado(id_cliente, id_documento);
    }

    public List<Documento> obtenerDocumentosAlquiladosActivosPorCliente(Cliente cliente) {

        return alquilerDAO.verDocumentosAlquiladosPorCliente(cliente.getId_usuario());
    }

    public void verificarYEstablecerEstadoAlquiler(Cliente cliente) {

        List<Alquiler> alquileres_activos = alquilerDAO.obtenerAlquileresActivosPorCliente(cliente.getId_usuario());

        for (Alquiler alquiler : alquileres_activos) {

            if (alquiler.getFecha_fin().isBefore(LocalDateTime.now())) {

                alquilerDAO.finalizarAlquiler(alquiler.getId_alquiler());
            }
        }
    }
}
