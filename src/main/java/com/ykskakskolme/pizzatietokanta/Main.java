package com.ykskakskolme.pizzatietokanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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

            List<Pizza> pizzat = pizzaDao.findAll();
            // Tässä on pieni hack, pitää varmaan tulevaisuudessa käsitellä nimet vasta index.html:ssä
            List<String> nimet = pizzat.stream().map(n -> n.getNimi()).collect(Collectors.toList());
            
            HashMap map = new HashMap<>();

            map.put("lista", nimet);
            map.put("pizzat", pizzat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            //Parametri osoitteesta
            Integer pizzaId = Integer.parseInt(req.params(":id"));
            //Haetaan pizza
            Pizza p = pizzaDao.findOne(pizzaId);
            //Näytetään pizza
            map.put("pizza", p);
            return new ModelAndView(map, "pizza");
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
            
            pizzaDao.delete(Integer.parseInt(req.params(":id")));

            // avaa yhteys tietokantaan
           /* Connection conn = getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM PizzaAnnos WHERE nimi = ?");
            stmt.setString(1, req.params(":id"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close(); */

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
