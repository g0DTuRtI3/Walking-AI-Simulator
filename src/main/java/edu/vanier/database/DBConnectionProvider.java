package edu.vanier.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zeyu Huang
 * REF:https://github.com/frostybee/fx-gallery
 */

/**
 * A singleton class that enables connection to an SQLite database.
 */
public abstract class DBConnectionProvider {

    /**
     * Opens a connection to an SQLite database.
     *
     * @param databaseName the name of the SQLite database file (it can also be
     * a relative path.
     * @return an opened connection to an SQLite database.
     */
    public Connection getConnection(){
        try {
            String db_location = DBConnectionProvider.class.getResource("/data/database.db").toExternalForm();
            Class.forName("org.sqlite.JDBC");
            Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:" + db_location);
            return dbConnection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}

