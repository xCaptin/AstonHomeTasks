package by.xCaptin.restapi.db;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Config config = ConfigFactory.load();
    private static final String DB_URL = config.getString("db.url");
    private static final String USER = config.getString("db.username");
    private static final String PASS = config.getString("db.password");
    private static final String DRIVER = config.getString("db.driver");
    private static HikariDataSource dataSource;

    public static void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER);
        config.setPassword(PASS);
        config.setDriverClassName(DRIVER);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            init();
        }
        return dataSource.getConnection();
    }
}
