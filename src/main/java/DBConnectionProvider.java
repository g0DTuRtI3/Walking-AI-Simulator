
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Zeyu Huang
 */
public abstract class DBConnectionProvider {
    public Connection getConnection() {
        String dbConnection = DBConnectionProvider.class.getResource("/data/database.db").toExternalForm();
        
    }
}
