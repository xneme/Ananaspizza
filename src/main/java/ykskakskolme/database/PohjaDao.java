package ykskakskolme.database;

import ykskakskolme.domain.Pohja;
import java.sql.*;
import java.util.*;

public class PohjaDao implements Dao<Pohja, Integer> {

    private Database database;

    public PohjaDao(Database database) {
        this.database = database;
    }

    @Override
    public Pohja findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pohja WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();

            return null;
        }

        Pohja p = new Pohja(rs.getInt("id"), rs.getString("nimi"));

        rs.close();
        stmt.close();
        conn.close();

        return p;
    }

    @Override
    public List<Pohja> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pohja");
        List<Pohja> pohjat = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            pohjat.add(new Pohja(rs.getInt("id"), rs.getString("nimi")));
        }

        rs.close();
        stmt.close();
        conn.close();

        return pohjat;
    }

    public Pohja findByPizzaId(Integer pizzaId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pohja, Pizza WHERE Pohja.id = Pizza.pohja_id AND Pizza.id = ?");
        stmt.setInt(1, pizzaId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            
            return null;
        }

        Pohja p = new Pohja(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        conn.close();
        
        return p;
    }

    @Override
    public Pohja saveOrUpdate(Pohja object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pohja (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Pohja WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt = conn.prepareStatement("UPDATE Pizza SET pohja_id = 1 WHERE pohja_id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

}
