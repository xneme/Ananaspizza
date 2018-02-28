package ykskakskolme.database;

import ykskakskolme.domain.Tayte;
import java.sql.*;
import java.util.*;
import ykskakskolme.domain.Tilastoalkio;

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
    
    public List<Tilastoalkio> tilasto() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT nimi, COUNT(*) AS maara FROM (SELECT DISTINCT Tayte.nimi AS nimi, Pizza.id FROM Tayte, Pizza, PizzaTayte WHERE PizzaTayte.pizza_id = Pizza.id AND PizzaTayte.tayte_id = Tayte.id) AS alikyselyllaonpakkoollanimi GROUP BY nimi ORDER BY maara DESC LIMIT 5");
        ResultSet rs = stmt.executeQuery();
        List<Tilastoalkio> tilasto = new ArrayList<>();
        
        while (rs.next()) {
            tilasto.add(new Tilastoalkio(rs.getString("nimi"), rs.getInt("maara")));
        }

        rs.close();
        stmt.close();
        conn.close();

        return tilasto;
    }

    public List<Tayte> findByPizzaId(Integer pizzaId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT PizzaTayte.id AS id, Tayte.nimi AS nimi, Tayte.vegaaninen AS vegaaninen, PizzaTayte.ohje as ohje FROM Tayte, PizzaTayte, Pizza WHERE Tayte.id = PizzaTayte.tayte_id AND PizzaTayte.pizza_id = Pizza.id AND Pizza.id = ? ORDER BY id");
        stmt.setInt(1, pizzaId);
        List<Tayte> taytteet = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            taytteet.add(new Tayte(rs.getInt("id"), rs.getString("nimi"), rs.getBoolean("vegaaninen"), rs.getString("ohje")));
        }

        if (!taytteet.isEmpty()) {
            taytteet.get(0).setNoDelete();
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM PizzaTayte WHERE tayte_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM Tayte WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        

        stmt.close();
        conn.close();
    }

}
