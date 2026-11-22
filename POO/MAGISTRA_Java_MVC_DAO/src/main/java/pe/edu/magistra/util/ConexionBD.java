package pe.edu.magistra.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/magistra?serverTimezone=America/Lima&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("No se encontro MySQL Connector/J. Revise que Maven haya descargado las dependencias del pom.xml.");
        }
    }

    private ConexionBD() {
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, claveLocal());
    }

    private static String claveLocal() throws SQLException {
        String entorno = System.getenv("MAGISTRA_DB_PASSWORD");
        if (entorno != null) {
            return entorno;
        }
        try (Reader lector = Files.newBufferedReader(Path.of("conexion.local.properties"))) {
            Properties ajustes = new Properties();
            ajustes.load(lector);
            return ajustes.getProperty("password", "");
        } catch (NoSuchFileException e) {
            return "";
        } catch (IOException | IllegalArgumentException e) {
            throw new SQLException("No se pudo leer conexion.local.properties.", e);
        }
    }
}
