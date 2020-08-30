package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection dbDriver;
    private Connection connection;
    private String url = "jdbc:mysql://localhost/library";
    private String username = "root";
    private String password = "";

    private DatabaseConnection() throws SQLException {
        try {
            
            this.connection = DriverManager.getConnection(url, username, password);
            
        } catch (Exception ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getdbDriver() throws SQLException {
        if (dbDriver == null) {
        	dbDriver = new DatabaseConnection();
        } else if (dbDriver.getConnection().isClosed()) {
        	dbDriver = new DatabaseConnection();
        }

        return dbDriver;
    }

}
