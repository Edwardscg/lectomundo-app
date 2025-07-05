package com.lectomundo.repository.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase auxiliar encargada de establecer la conexión con la base de datos TiDB Cloud.
 * Centraliza la configuración de conexión para evitar la repetición de credenciales y URLs.
 */
public class DBConexion {

    // URL de conexión JDBC a la base de datos TiDB Cloud
    private static final String URL = "jdbc:mysql://gateway01.us-east-1.prod.aws.tidbcloud.com:4000/biblioteca_virtual";

    // Usuario de la base de datos
    private static final String user = "2nrjKr1bzyGsGm5.root";

    // Contraseña del usuario
    private static final String password = "deKZTb4j3LAOhlMK";

    /**
     * Establece una conexión con la base de datos TiDB Cloud usando JDBC.
     *
     * @return objeto Connection listo para usar en operaciones SQL
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection establecerConexion() throws SQLException {

        return DriverManager.getConnection(URL, user, password);
    }
}
