/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.cinecito.entities;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.itson.cinecito.persistence.MySQLConnection;

/**
 *
 * @author avalo
 */
public class Cinecito {


    private int idMovie;
    private int idFunctions;
    private String name;
    private String time;
    private String room;
    private String sinopsys;
    private String gender;

    /**
     * Este métod esta diseñado para recuperar una lista de objetos de tipo
     * Cinecito desde una base de datos.
     *
     * @param filtro
     * @return cinecitos
     */
    public static List<Cinecito> getAll(String filtro) {
        List<Cinecito> cinecitos = new ArrayList<>();

        try {
            Connection conexion = MySQLConnection.get();
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM movie WHERE name LIKE ?");
            statement.setString(1, "%" + filtro + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Cinecito c = new Cinecito();
                c.setIdMovie(resultSet.getInt(1));
                c.setName(resultSet.getString(2));
                c.setSinopsys(resultSet.getString(3));
                c.setGender(resultSet.getString(4));
                cinecitos.add(c);
            }

        } catch (SQLException ex) {

        }
        return cinecitos;
    }

    /**
     * Este método esta diseñado para agregar/guardad nuevo dato en la base de
     * datos de nombre Cinecito.
     *
     * @param name
     * @param room
     * @param time
     * @return result
     */
    public boolean save(String name, String sinopsys, String gender) {

        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "INSERT INTO movie (name, sinopsys, gender) VALUES (?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, sinopsys);
            statement.setString(3, gender);
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        return result;
    }

    /**
     * Este método esta diseñado para actualizar/modificar algun dato en base la
     * id solicitada.
     *
     * @param idMovie
     * @param name
     * @param room
     * @param time
     * @return result
     */
    public boolean update(int idMovie, String name, String sinopsys, String gender) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();

            String query = "UPDATE movie SET name = ?, sinopsys = ?, gender = ? WHERE idMovie = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, sinopsys);
            statement.setString(3, gender);
            statement.setInt(4, idMovie);
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();

        } catch (Exception ex) {

            System.err.println("Error: " + ex.getMessage());
        }

        return result;
    }

    /**
     * Con este metodo ingresando la id se elimina por completo el dato de la
     * base de datos
     *
     * @param idMovie
     * @return result
     */
    public boolean delete(int idMovie) {
        boolean result = false;
        try {
            Connection conexion = MySQLConnection.get();
            String query = "DELETE FROM movie WHERE idMovie = ?";
            PreparedStatement statement = conexion.prepareStatement(query);

            statement.setInt(1, idMovie);
            statement.execute();

            result = statement.getUpdateCount() == 1;

            conexion.close();

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        return result;
    }

    /**
     * @return the idMovie
     */
    public int getIdMovie() {
        return idMovie;
    }

    /**
     * @param idMovie the idMovie to set
     */
    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the sinopsys
     */
    public String getSinopsys() {
        return sinopsys;
    }

    /**
     * @param sinopsys the sinopsys to set
     */
    public void setSinopsys(String sinopsys) {
        this.sinopsys = sinopsys;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return the idFunction
     */
    public int getIdFunction() {
        return idFunctions;
    }

    /**
     * @param idFunction the idFunction to set
     */
    public void setIdFunction(int idFunction) {
        this.idFunctions = idFunction;
    }

}
