package ykskakskolme.database;

import ykskakskolme.database.Database;
import ykskakskolme.database.Dao;
import ykskakskolme.domain.Koko;
import java.sql.*;
import java.util.*;

public class KokoDao implements Dao<Koko, Integer> {

    private Database database;

    public KokoDao(Database database) {
        this.database = database;
    }

    @Override
    public Koko findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Koko WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }

        Koko k = new Koko(rs.getInt("id"), rs.getString("nimi"));

        rs.close();
        stmt.close();
        conn.close();

        return k;
    }

    @Override
    public List<Koko> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Koko");
        List<Koko> koot = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            koot.add(new Koko(rs.getInt("id"), rs.getString("nimi")));
        }
        rs.close();
        stmt.close();
        conn.close();

        return koot;
    }

    public Koko findByPizzaId(Integer pizzaId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Koko, Pizza WHERE Koko.id = Pizza.koko_id AND Pizza.id = ?");
        stmt.setInt(1, pizzaId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();

            return null;
        }

        Koko k = new Koko(rs.getInt("id"), rs.getString("nimi"));

        rs.close();
        stmt.close();
        conn.close();

        return k;
    }

    @Override
    public Koko saveOrUpdate(Koko object) throws SQLException {
        // tällä hetkellä vain save, ja palauttaa inputin ilman id:tä 
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Koko (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();
        conn.close();

        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Koko WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
