package com.lectomundo.repository.dao;

import com.lectomundo.model.Alquiler;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.Date;
import java.sql.Timestamp;

public class AlquilerDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = new DocumentoDAO();

    public void registrarAlquiler(Alquiler alquiler) throws Exception {

        String sql = "INSERT INTO alquiler (id_usuario, id_libro, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";

        DBHelper.manejarEntidad(sql, alquiler.getCliente().getId_usuario(), alquiler.getDocumento().getId_documento(), Timestamp.valueOf(alquiler.getFecha_inicio()), Timestamp.valueOf(alquiler.getFecha_fin()), alquiler.getEstado_alquiler());
    }

    public void finalizarAlquiler(int id_alquiler) throws Exception {
        String sql = "UPDATE alquiler SET estado = 'finalizado' WHERE id_alquiler = ?";
        DBHelper.manejarEntidad(sql, id_alquiler);
    }
    

}
