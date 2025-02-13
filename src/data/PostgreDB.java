package data;

import data.interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDB implements IDB {
    private static PostgreDB instance;
    private Connection connection;
    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "26120331de";

    private PostgreDB() {
        connect();
    }

    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static PostgreDB getInstance() {
        if (instance == null) {
            instance = new PostgreDB();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect(); // Восстанавливаем соединение
            }
        } catch (SQLException e) {
            System.out.println("Failed to get connection: " + e.getMessage());
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
