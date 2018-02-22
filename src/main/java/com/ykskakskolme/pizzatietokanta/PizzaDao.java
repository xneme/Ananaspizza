/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykskakskolme.pizzatietokanta;
import java.util.*;
import java.sql.*;

public class PizzaDao implements Dao<Pizza, Integer>{
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
        Pizza p = new Pizza(rs.getString("nimi"));
        rs.close();
        stmt.close();
        conn.close();
        return p;
        
    }

    @Override
    public List<Pizza> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pizza saveOrUpdate(Pizza object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}