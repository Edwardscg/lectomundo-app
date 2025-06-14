package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

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

    public Membresia buscarMembresiaPorId(int id_membresia) throws Exception {

        String sql = "SELECT * FROM membresia WHERE id_membresia = ?";

        return DBHelper.obtenerEntidad(sql, this::mapearMembresia, id_membresia);
    }

    public boolean tieneMembresiaActiva(int id_usuario) throws Exception {

        String sql = "SELECT COUNT(*) FROM membresia WHERE id_usuario = ? AND estado = ?";

        Integer tiene_membresia = DBHelper.obtenerEntidad(sql, rs -> 1, id_usuario, Estado.activo.toString());

        return tiene_membresia !=null;
    }

    public List<Membresia> verMembresiasPorUsuario(int id_usuario) throws Exception {

        String sql = "SELECT * FROM membresia WHERE id_usuario = ? ORDER BY fecha_inicio DESC";

        return DBHelper.obtenerListaEntidad(sql, this::mapearMembresia, id_usuario);
    }

    public ObservableList<Membresia> verMembresias() throws Exception {

        String sql = "SELECT * FROM membresia WHERE estado = 'activa' ORDER BY fecha_inicio DESC";

        return DBHelper.llenarTabla(sql, rs -> new Membresia(rs.getInt("id_membresia"), (Cliente) usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario")), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(), rs.getInt("costo"), Estado.valueOf(rs.getString("estado"))));
    }

    private Membresia mapearMembresia(ResultSet rs) throws Exception{

        Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

        return new Membresia(rs.getInt("id_membresia"), (Cliente) usuario, rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(), rs.getInt("costo"), Estado.valueOf(rs.getString("estado")));
    }
}
