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
    private TayteDao tayteDao;
    private PohjaDao pohjaDao;
    private KastikeDao kastikeDao;
    private KokoDao kokoDao;

    public PizzaDao(Database database, PohjaDao pohjaDao, KastikeDao kastikeDao, TayteDao tayteDao, KokoDao kokoDao) {
        this.database = database;
        this.tayteDao = tayteDao;
        this.pohjaDao = pohjaDao;
        this.kastikeDao = kastikeDao;
        this.kokoDao = kokoDao;
    }

    @Override
    public Pizza findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection(); // luodaan yhteys
        //valmistellaan statement turvallisesti
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pizza WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null; // jos ei löytynyt mitään, palautetaan null
        }

        List<Tayte> taytteet = tayteDao.findByPizzaId(key);
        Pohja pohja = pohjaDao.findByPizzaId(key);
        Kastike kastike = kastikeDao.findByPizzaId(key);
        Koko koko = kokoDao.findByPizzaId(key);

        Pizza p = new Pizza(rs.getInt("id"), rs.getString("nimi"), pohja, kastike, taytteet, koko, rs.getDouble("hinta"));
        rs.close();
        stmt.close();
        conn.close();
        return p;

    }

    @Override
    public List<Pizza> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pizza");
        List<Pizza> pizzat = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            List<Tayte> taytteet = tayteDao.findByPizzaId(id);
            Pohja pohja = pohjaDao.findByPizzaId(id);
            Kastike kastike = kastikeDao.findByPizzaId(id);
            Koko koko = kokoDao.findByPizzaId(id);
            
            pizzat.add(new Pizza(id, rs.getString("nimi"), pohja, kastike, taytteet, koko, rs.getDouble("hinta")));
        }

        return pizzat;

    }

    @Override
    public Pizza saveOrUpdate(Pizza object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pizza (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        return object;
    }
    
    public int saveRaw(String nimi, int pohjaId, int kastikeId, int kokoId, double hinta) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pizza (nimi, pohja_id, kastike_id, koko_id, hinta) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, nimi);
        stmt.setInt(2, pohjaId);
        stmt.setInt(3, kastikeId);
        stmt.setInt(4, kokoId);
        stmt.setDouble(5, hinta);
        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("SELECT * FROM Pizza WHERE nimi = ? AND pohja_id = ? AND kastike_id = ? AND koko_id = ? AND hinta = ?");
        stmt.setString(1, nimi);
        stmt.setInt(2, pohjaId);
        stmt.setInt(3, kastikeId);
        stmt.setInt(4, kokoId);
        stmt.setDouble(5, hinta);
        
        ResultSet rs = stmt.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        rs.close();
        stmt.close();
        conn.close();
        
        return id;
    }
    
    public void lisaaTayte(Integer pizzaId, Integer tayteId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO PizzaTayte (pizza_id, tayte_id) VALUES (?, ?)");
        stmt.setInt(1, pizzaId);
        stmt.setInt(2, tayteId);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Pizza WHERE id=?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
