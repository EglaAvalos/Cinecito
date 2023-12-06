/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.cinecito.entities;

import mx.itson.cinecito.persistence.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Se crean los metodos para realizar un CRUD en functions
 * 
 * @author avalo
 */
public class Function extends Cinecito {

    Cinecito cine = new Cinecito();

    /**
     * Este método esta diseñado para recuperar una lista de objetos de tipo
     * Cinecito desde una base de datos.
     *
     * @param filtro
     * @return function
     */
    public static List<Function> getAlls(String filtro) {
        List<Function> function = new ArrayList<>();

        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM functions WHERE name LIKE ?");
            statement.setString(1, "%" + filtro + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Function e = new Function();
                e.setIdFunction(resultSet.getInt(1));
                e.setName(resultSet.getString(2));
                e.setRoom(resultSet.getString(3));
                e.setTime(resultSet.getString(4));
                function.add(e);
            }

        } catch (SQLException ex) {

        }
        return function;
    }

    /**
     *
     * @param name
     * @param room
     * @param time
     * @return
     */
    public boolean saveFunction(String name, String room, String time) {
        try {
            Connection connection = MySQLConnection.get();
            // Verificar si la sala y hora están disponibles antes de insertar la función
            if (available(room, time)) {
                // Si la sala y hora están disponibles, insertar la función en la tabla functions
                String insertQuery = "INSERT INTO functions (name, room, time) VALUES (?, ?, ?)";

                try ( PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, name);
                    insertStatement.setString(2, room);
                    insertStatement.setString(3, time);
                    insertStatement.executeUpdate();
                    return true; // Indicar que la inserción fue exitosa
                }
            } else {
                String mensaje = "La sala y hora ya están ocupadas. No se puede agregar la función.";
                JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Verifica la disponibilidad de una sala en un determinado momento.
     *
     * @param room
     * @param time
     * @return
     */
    public boolean available(String room, String time) {
        try {
            //Se conecta a la base datos
            Connection conexion = MySQLConnection.get();

            //Prepara el comando para la base datos
            String query = "SELECT COUNT(*) FROM functions WHERE room = ? AND time = ?";
            try ( PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, room);
                statement.setString(2, time);

                try ( ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtiene el conteo de funciones para la sala y momento específicos
                        int count = resultSet.getInt(1);
                        return count == 0;
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }

        return false;
    }

    /**
     * Con este metodo ingresando la id se elimina por completo el dato de la
     * base de datos
     *
     * @param idFunctions
     * @return
     */
    public boolean delete(int idFunctions) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "DELETE FROM functions WHERE idFunctions = ?";
            PreparedStatement statement = conexion.prepareStatement(query);

            statement.setInt(1, idFunctions);
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        return result;
    }

    public boolean updates(int idFunctions, String room, String time) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            if (available(room, time)) {
                String query = "UPDATE functions SET room = ?, time = ? WHERE idFunctions = ?";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setString(1, room);
                statement.setString(2, time);
                statement.setInt(3, idFunctions);
                statement.execute();

                result = statement.getUpdateCount() == 1;

                conexion.close();
            } else {
                String mensaje = "La sala y hora ya están ocupadas. No se puede agregar la función.";
                JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {

            System.err.println("Error: " + ex.getMessage());
        }

        return result;
    }

}
