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

        PohjaDao pohjaDao = new PohjaDao(database);
        KastikeDao kastikeDao = new KastikeDao(database);
        TayteDao tayteDao = new TayteDao(database);
        KokoDao kokoDao = new KokoDao(database);
        PizzaDao pizzaDao = new PizzaDao(database, pohjaDao, kastikeDao, tayteDao, kokoDao);

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        System.out.println("Hello world!");

        Spark.get("/", (req, res) -> {

            List<Pizza> pizzat = pizzaDao.findAll();

            HashMap map = new HashMap<>();

            map.put("pizzat", pizzat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/pizza/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            //Parametri osoitteesta
            Integer pizzaId = Integer.parseInt(req.params(":id"));
            //Haetaan pizza
            Pizza p = pizzaDao.findOne(pizzaId);
            //Näytetään pizza
            map.put("pizza", p);
            return new ModelAndView(map, "pizza");
        }, new ThymeleafTemplateEngine());

        Spark.get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();
            
            List<Pohja> pohjat = pohjaDao.findAll();
            List<Kastike> kastikkeet = kastikeDao.findAll();
            List<Tayte> taytteet = tayteDao.findAll();

            map.put("pohjat", pohjat);
            map.put("kastikkeet", kastikkeet);
            map.put("taytteet", taytteet);
            
            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());

        Spark.get("/taytteet", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Pohja> pohjat = pohjaDao.findAll();
            List<Kastike> kastikkeet = kastikeDao.findAll();
            List<Tayte> taytteet = tayteDao.findAll();

            map.put("pohjat", pohjat);
            map.put("kastikkeet", kastikkeet);
            map.put("taytteet", taytteet);

            return new ModelAndView(map, "taytteet");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/pizzantaytteet", (req, res) -> {
            String nimi = req.queryParams("pizza");
            
            System.out.println("Saatiin: "
                    + req.queryParams("pizza"));

            Pizza p = new Pizza(null, req.queryParams("pizza"));
            pizzaDao.saveOrUpdate(p);

            res.redirect("/");
            return "";
        });

        Spark.post("/lisaatayte", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("tayte") + " "
                    + req.queryParams("vegaaninen"));

            Boolean vegaaninen = req.queryParamOrDefault("vegaaninen", "false").equals("true");
            Tayte t = new Tayte(null, req.queryParams("tayte"), vegaaninen);
            tayteDao.saveOrUpdate(t);

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/lisaapohja", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("pohja"));

            Pohja p = new Pohja(null, req.queryParams("pohja"));
            pohjaDao.saveOrUpdate(p);

            res.redirect("/taytteet");
            return "";
        });
        
        Spark.post("/lisaakastike", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("kastike"));

            Kastike k = new Kastike(null, req.queryParams("kastike"));
            kastikeDao.saveOrUpdate(k);

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/poistapizza/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            pizzaDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/");
            return "";
        });
        
        Spark.post("/poistatayte/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            tayteDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/taytteet");
            return "";
        });
    }
}
