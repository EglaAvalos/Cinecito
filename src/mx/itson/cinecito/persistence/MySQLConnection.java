/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.cinecito.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author avalo
 */
public class MySQLConnection {

     public static Connection get (){
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/cinecito?user=root&password=aliz");
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return connection;
}

}
