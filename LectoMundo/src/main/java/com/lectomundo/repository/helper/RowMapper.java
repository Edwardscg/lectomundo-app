package com.lectomundo.repository.helper;

import java.sql.ResultSet;

// Interfaz funcional para mapear una fila del ResultSet a un objeto de tipo T.
public interface RowMapper <T>{

    T mapRow(ResultSet rs ) throws Exception;
}
