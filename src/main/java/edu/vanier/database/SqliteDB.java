package edu.vanier.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Zeyu Huang
 *
 * REF:https://github.com/frostybee/fx-gallery
 */
public class SqliteDB extends DBConnectionProvider {

    private final String TABLE = "data";

    public SqliteDB() {
    }

    /**
     * This method opens a connection to the database.
     * 
     * @return The connection
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/main/resources/data/database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * This method gets the name of every walker in the database.
     * 
     * @return An ArrayList if the names.
     */
    public ArrayList<String> readModelName() {
        String sql = "SELECT * FROM data";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            ArrayList<String> name_List = new ArrayList<>();
            while (rs.next()) {
                name_List.add(rs.getString("Name"));
            }
            return name_List;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * This method gets the byte array of the walker base on its name.
     * 
     * @param modelName The name of the walker
     * @return The byte array of the walker.
     */
    public byte[] readModel(String modelName) {
        String sql = "SELECT Walker FROM data WHERE Name = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("Walker");
            } else {
                System.out.println("Model not found: " + modelName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * This method prints all the model that exist in the database.
     */
    public void printAllModel() {
        String sql = "SELECT * FROM data";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("Id") + "\t" + rs.getString("Name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This methods adds the byte array of the walker and its name into the database.
     * 
     * @param b_Array The byte array of the walker.
     * @param modelName The name of the walker
     */
    public void addModel(byte[] b_Array, String modelName) {
        String sql = "INSERT INTO data (Name, Walker) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelName);
            pstmt.setBytes(2, b_Array);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("model added");
    }
    
    /**
     * This method deletes a model from the database.
     * 
     * @param b_Array The byte array of the walker model.
     */
    public void deleteModel(byte[] b_Array) {
        String query = String.format("DELETE FROM %s WHERE Walker = ? ", TABLE);
        try (Connection dbConnection = getConnection(); PreparedStatement pstmt = dbConnection.prepareStatement(query)) {
            // set parameters
            pstmt.setBytes(1, b_Array);
            pstmt.executeUpdate();
            System.out.println("The selected item has been removed from there DB...");
        } catch (SQLException e) {
            System.out.println("An error has occured with the message: " + e);
        }
    }

    public void editModel(byte[] b_Array) {
        String query = String.format("UPDATE %s SET Name = ?, Walker = ? WHERE id = %d", TABLE);
    }
}
