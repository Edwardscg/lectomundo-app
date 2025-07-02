package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class MembresiaDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarMembresia(Membresia membresia) {

        String sql = "INSERT INTO membresia (id_usuario, fecha_inicio, fecha_fin, costo) VALUES (?, ?, ?, ?)";

        DBHelper.manejarEntidad(sql, membresia.getCliente().getId_usuario(), membresia.getFecha_inicio(), membresia.getFecha_fin(), membresia.getPrecio());
    }

    public void actualizarMembresia(int id_usuario, LocalDate fecha_fin) {

        String sql = "UPDATE membresia SET fecha_fin = ? WHERE id_usuario = ?";

        DBHelper.manejarEntidad(sql, fecha_fin, id_usuario);
    }

    public void finalizarMembresia(int id_usuario, LocalDate fecha_fin) {

        String sql = "UPDATE membresia SET estado = ?, fecha_fin = ? WHERE id_usuario = ? AND estado = 'activo'";

        DBHelper.manejarEntidad(sql, Estado.finalizado.toString(), fecha_fin, id_usuario);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public Membresia buscarMembresiaPorId(int id_membresia) {

        String sql = "SELECT * FROM membresia WHERE id_membresia = ?";

        return DBHelper.obtenerEntidad(sql, this::mapearMembresia, id_membresia);
    }
    */

    public boolean tieneMembresiaActiva(int id_usuario) {

        String sql = "SELECT * FROM membresia WHERE id_usuario = ? AND estado = 'activo' AND CURDATE() BETWEEN fecha_inicio AND fecha_fin";

        return DBHelper.obtenerEntidad(sql, rs -> true, id_usuario) != null;
    }

    public ObservableList<Membresia> verMembresias() {

        String sql = "SELECT m.*, u.tipo FROM membresia m  JOIN usuario u ON m.id_usuario = u.id_usuario WHERE m.estado = 'activo' AND u.tipo = 'cliente'  ORDER BY m.fecha_inicio DESC";

        return DBHelper.llenarTabla(sql, rs -> {
            Cliente cliente = (Cliente) usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new Membresia(rs.getInt("id_membresia"), cliente, rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(), rs.getInt("costo"), Estado.valueOf(rs.getString("estado")));
        });
    }

    /*
    FUTURA IMPLEMENTACIÓN

    private Membresia mapearMembresia(ResultSet rs) {

        try {

            Usuario usuario = usuarioDAO.buscarUsuarioPorId(rs.getInt("id_usuario"));

            return new Membresia(rs.getInt("id_membresia"), (Cliente) usuario, rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(), rs.getInt("costo"), Estado.valueOf(rs.getString("estado")));

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos de membresía desde la Base de Datos.");
        }
    }
    */
}
