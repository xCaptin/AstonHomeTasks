package by.xCaptin.restapi.db;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    private static final Config config = ConfigFactory.load();
    private static final String DB_URL = config.getString("db.url");
    private static final String USER = config.getString("db.username");
    private static final String PASS = config.getString("db.password");
    private static final String DRIVER = config.getString("db.driver");
    static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            System.out.println("SQLException: " + e.getException());
        }

        return connection;
    }
}
