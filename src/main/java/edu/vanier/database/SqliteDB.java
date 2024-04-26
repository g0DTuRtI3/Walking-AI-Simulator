package edu.vanier.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Zeyu Huang
 *
 * REF:https://github.com/frostybee/fx-gallery
 */
public class SqliteDB extends DBConnectionProvider {

    private final String TABLE = "data";
    private final String COL_NAME = "Name";
    private final String COL_WALKER = "Walker";

    public SqliteDB() {
    }

    public void addModel(byte[] b_Array, String modelName){
        String query = String.format("INSERT INTO %s (%s,%s) VALUES(?,?)", TABLE, COL_NAME, COL_WALKER);
        try (Connection dbConnection = getConnection(); PreparedStatement pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //-- 1) Set parameters and insert the new item.
            pstmt.setString(1, modelName);
            pstmt.setBytes(2, b_Array);
            pstmt.executeUpdate();
            System.out.println("Storing the data into the database");

            //-- 2) Retrieve the lastly generate ID.
            ResultSet resultSet = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }
            System.out.println("New model ID: " + generatedKey);
        } catch (SQLException e) {
            System.out.println("An error has occured with the message: " + e);
        }
    }
}
