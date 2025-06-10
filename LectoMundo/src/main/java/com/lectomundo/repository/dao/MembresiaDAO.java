package com.lectomundo.repository.dao;

import com.lectomundo.model.Estado;
import com.lectomundo.model.Membresia;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.time.LocalDate;

public class MembresiaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarMembresia(Membresia membresia) throws Exception {

        String sql = "INSERT INTO membresia (id_usuario, fecha_inicio, fecha_fin, costo, estado) VALUES (?, ?, ?, ?, ?)";

        DBHelper.manejarEntidad(sql, membresia.getCliente().getId_usuario(), Date.valueOf(membresia.getFecha_inicio()), Date.valueOf(membresia.getFecha_fin()), membresia.getPrecio(), membresia.getEstado_membresia().toString());
    }

    public void actualizarMembresia(Membresia membresia) throws Exception {

        LocalDate nuevaFechaFin = membresia.getFecha_fin().plusDays(30);

        String sql = "UPDATE membresia SET fecha_fin = ? WHERE id_membresia = ?";

        DBHelper.manejarEntidad(sql, Date.valueOf(nuevaFechaFin), membresia.getId_membresia());

        membresia.setFecha_fin(nuevaFechaFin);
    }

    public void finalizarMembresia(int id_membresia) throws Exception {

        String sql = "UPDATE membresia SET estado = ?, fecha_fin = ? WHERE id_membresia = ?";

        DBHelper.manejarEntidad(sql, Estado.finazlizado.toString(), Date.valueOf(LocalDate.now()), id_membresia);
    }
}
