/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykskakskolme.pizzatietokanta;

import java.util.*;
import java.sql.*;

public class PizzaDao implements Dao<Pizza, Integer> {

    private Database database;

    public PizzaDao(Database database) {
        this.database = database;
    }

    @Override
    public Pizza findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection(); // luodaan yhteys
        //valmistellaan statement turvallisesti
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PizzaAnnos WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null; // jos ei löytynyt mitään, palautetaan null
        }
        // TODO pizzalla on tällä hetkellä vain nimi
        Pizza p = new Pizza(rs.getInt("id"), rs.getString("nimi"));
        rs.close();
        stmt.close();
        conn.close();
        return p;

    }

    @Override
    public List<Pizza> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PizzaAnnos");
        List<Pizza> pizzat = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            pizzat.add(new Pizza(rs.getInt("id"), rs.getString("nimi")));
        }

        return pizzat;

    }
    
    

    @Override
    public Pizza saveOrUpdate(Pizza object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO PizzaAnnos (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return object;
    }
    
 
    

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM PizzaAnnos WHERE id=?");
        
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    
}
