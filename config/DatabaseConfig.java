package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {


    private static final String URL = "jdbc:postgresql://localhost:5432/E-Ticketing_DB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    private static Connection connection;

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Connection failed: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
}
