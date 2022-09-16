package fr.pandaguerrier.conodiajob.database;

import fr.pandaguerrier.conodiajob.ConodiaJobs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + ConodiaJobs.instance.getDataFolder().getAbsolutePath() + "/database.db");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public  Connection getConnection() {
        return connection;
    }
}
