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
    private final String COL_NAME = "Name";
    private final String COL_WALKER = "Walker";

    public SqliteDB() {
    }

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

//    public byte[] readModel(String modelName) {
//        String sql = "SELECT * FROM data WHERE Name = " + modelName;
//
//        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//            // loop through the result set
//            System.out.println(rs.getInt("Id") + "\t" + rs.getString("Name"));
//            return rs.getBytes("Walker");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            System.out.println("Oh no not found !");
//        }
//        return null;
//    }
    
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

    //Fpublic void insertModel(String name) {
    public void addModel(byte[] b_Array, String modelName) {
        String sql = "INSERT INTO data (Name, Walker) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelName);
            pstmt.setBytes(2, b_Array);
            //pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("model added");
    }

    /*
    public void addModel(byte[] b_Array, String modelName) {
        String query = String.format("INSERT INTO %s (%s,%s) VALUES(?,?)", TABLE, COL_NAME, COL_WALKER);
        try (Connection dbConnection = getConnection(); PreparedStatement pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //-- 1) Set parameters and insert the new item.
            dbConnection.setAutoCommit(true);            
            pstmt.setString(1, modelName);
            pstmt.setBytes(2, b_Array);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(" Nbr. Row Affected: "+ rowsAffected);
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
    }*/
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
