package com.ykskakskolme.pizzatietokanta;

import java.sql.*;
import java.util.*;

public class TayteDao implements Dao<Tayte, Integer> {

    private Database database;

    public TayteDao(Database database) {
        this.database = database;
    }

    @Override
    public Tayte findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tayte WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            
            return null;
        }

        Tayte t = new Tayte(rs.getInt("id"), rs.getString("nimi"), rs.getBoolean("vegaaninen"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return t;
    }

    public List<Tayte> tyhjaLista() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tayte WHERE name = ?");
        stmt.setString(1, "Ananas");

        List<Tayte> taytteet = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            
            return taytteet;
        }

        Tayte ananas = new Tayte(rs.getInt("id"), rs.getString("nimi"), rs.getBoolean("vegaaninen"));
        taytteet.add(ananas);

        rs.close();
        stmt.close();
        conn.close();
        
        return taytteet;
    }

    @Override
    public List<Tayte> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tayte");
        List<Tayte> taytteet = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            taytteet.add(new Tayte(rs.getInt("id"), rs.getString("nimi"), rs.getBoolean("vegaaninen")));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return taytteet;
    }

    public List<Tayte> findByPizzaId(Integer pizzaId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tayte, PizzaTayte, Pizza WHERE Tayte.id = PizzaTayte.tayte_id AND PizzaTayte.pizza_id = Pizza.id AND Pizza.id = ?");
        stmt.setInt(1, pizzaId);
        List<Tayte> taytteet = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            taytteet.add(new Tayte(rs.getInt("id"), rs.getString("nimi"), rs.getBoolean("vegaaninen")));
        }

        rs.close();
        stmt.close();
        conn.close();

        return taytteet;
    }

    @Override
    public Tayte saveOrUpdate(Tayte object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tayte (nimi, vegaaninen) VALUES (?, ?)");
        stmt.setString(1, object.getNimi());
        stmt.setBoolean(2, object.getVegaaninen());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tayte WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt = conn.prepareStatement("DELETE FROM PizzaTayte WHERE tayte_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
