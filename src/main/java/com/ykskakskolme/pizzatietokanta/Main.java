package com.ykskakskolme.pizzatietokanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:pizzat.db");
        PizzaDao pizzaDao = new PizzaDao(database);
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        System.out.println("Hello world!");
        
        Spark.get("/", (req, res) -> {

            List<String> pizzat = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT nimi FROM PizzaAnnos");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String nimi = tulos.getString("nimi");
                pizzat.add(nimi);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("lista", pizzat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/", (req, res) -> {
            System.out.println("Hei maailma!");
            System.out.println("Saatiin: "
                    + req.queryParams("pizza"));

            // avaa yhteys tietokantaan
            Connection conn = getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("INSERT INTO PizzaAnnos (nimi) VALUES (?)");
            stmt.setString(1, req.queryParams("pizza"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();

            res.redirect("/");
            return "";
        });

        Spark.post("/delete/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            // avaa yhteys tietokantaan
            Connection conn = getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM PizzaAnnos WHERE nimi = ?");
            stmt.setString(1, req.params(":id"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();

            res.redirect("/");
            return "";
        });
    }

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:pizzat.db");
    }
}

