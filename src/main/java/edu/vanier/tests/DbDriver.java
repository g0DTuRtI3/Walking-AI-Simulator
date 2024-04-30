package edu.vanier.tests;

import edu.vanier.database.DBConnectionProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 2253883
 */
public class DbDriver extends DBConnectionProvider {

    private final String TABLE = "data";
    private final String COL_NAME = "Name";
    private final String COL_WALKER = "Walker";

    public static void main(String[] args) {
        System.out.println(" Test: inserting into the database....");
        DbDriver app = new DbDriver();
        //oi.addNewBruh("Youssef");
        
        app.insertModel("Raw Materials");
        app.readModel("10");
        //app.insert("Semifinished Goods", 4000);
        //app.insert("Finished Goods", 5000);

        // NOTE: Connection and Statement are AutoCloseable.
        //       Don't forget to close them both in order to avoid leaks.
        /*
        try (
                // create a database connection
                Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/data/database.db"); 
                Statement statement = connection.createStatement();) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //statement.executeUpdate("insert into bruh (first_name) values( 'Oi')");
            statement.executeUpdate("insert into data (Name, Walker) values( 'Oi')");
            //statement.executeUpdate("insert into person values( 'Youssefffff!')");
            ResultSet rs = statement.executeQuery("select * from data");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("Name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace(System.err);
        }*/
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
    
      public void readModel(String modelId) {
        String sql = "SELECT * FROM data WHERE Id = "+modelId;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("Id") +  "\t" + 
                                   rs.getString("Name") + "\t" +
                                   rs.getBytes("Walker"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertModel(String name) {
        String sql = "INSERT INTO data (Name) VALUES(?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            //pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
public void insert(String name, double capacity) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addNewBruh(String modelName) {
        String query = "INSERT INTO person (name) VALUES(?);";
        try (Connection dbConnection = getConnection(); 
                PreparedStatement pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //-- 1) Set parameters and insert the new item.
            //dbConnection.setAutoCommit(true);
            pstmt.setString(1, modelName);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.clearParameters();
            System.out.println(" Nbr. Row Affected: " + rowsAffected);
            System.out.println("Storing the data into the database");
            //dbConnection.commit();
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

    public void addModel(byte[] b_Array, String modelName) {
        String query = String.format("INSERT INTO %s (%s,%s) VALUES(?,?)", TABLE, COL_NAME, COL_WALKER);
        try (Connection dbConnection = getConnection(); PreparedStatement pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            //-- 1) Set parameters and insert the new item.
            dbConnection.setAutoCommit(true);
            pstmt.setString(1, modelName);
            pstmt.setBytes(2, b_Array);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(" Nbr. Row Affected: " + rowsAffected);
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
