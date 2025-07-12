package com.lectomundo.repository.helper;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase auxiliar encargada de establecer la conexión con la base de datos TiDB Cloud.
 * Centraliza la configuración de conexión para evitar la repetición de credenciales y URLs.
 */
public class DBConexion {

    private static String jdbc_url;
    private static String bd_user;
    private static String bd_password;

    static {

        try{

            Properties props = new Properties();

            // Ruta del archivo de configuración
            FileInputStream fis = new FileInputStream("db.properties");

            props.load(fis);
            jdbc_url = props.getProperty("JDBC_URL");
            bd_user= props.getProperty("BD_USER");
            bd_password = props.getProperty("BD_PASSWORD");

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    /**
     * Establece una conexión con la base de datos TiDB Cloud usando JDBC.
     *
     * @return objeto Connection listo para usar en operaciones SQL
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection establecerConexion() throws SQLException {

        return DriverManager.getConnection(jdbc_url, bd_user, bd_password);
    }
}
