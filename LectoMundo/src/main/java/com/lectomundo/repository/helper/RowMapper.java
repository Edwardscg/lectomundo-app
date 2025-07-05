package com.lectomundo.repository.helper;

import java.sql.ResultSet;

/**
 * Interfaz funcional que define cómo mapear una fila de ResultSet
 * a un objeto del tipo T.
 *
 * @param <T> tipo del objeto resultante
 */
@FunctionalInterface
public interface RowMapper<T> {

    T mapRow(ResultSet rs) throws Exception;
}
