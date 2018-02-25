package ykskakskolme.database;

import ykskakskolme.domain.Kastike;
import java.sql.*;
import java.util.*;

public class KastikeDao implements Dao<Kastike, Integer> {

    private Database database;

    public KastikeDao(Database database) {
        this.database = database;
    }

    @Override
    public Kastike findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kastike WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }

        Kastike k = new Kastike(rs.getInt("id"), rs.getString("nimi"));
        rs.close();
        stmt.close();
        conn.close();

        return k;
    }

    @Override
    public List<Kastike> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kastike");
        List<Kastike> kastikkeet = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            kastikkeet.add(new Kastike(rs.getInt("id"), rs.getString("nimi")));
        }

        rs.close();
        stmt.close();
        conn.close();

        return kastikkeet;
    }

    public Kastike findByPizzaId(Integer pizzaId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kastike, Pizza WHERE Kastike.id = Pizza.pohja_id AND Pizza.id = ?");
        stmt.setInt(1, pizzaId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }

        Kastike k = new Kastike(rs.getInt("id"), rs.getString("nimi"));
        rs.close();
        stmt.close();
        conn.close();
        return k;
    }

    @Override
    public Kastike saveOrUpdate(Kastike object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kastike (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();
        conn.close();

        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kastike WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
