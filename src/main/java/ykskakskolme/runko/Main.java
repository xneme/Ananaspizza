package ykskakskolme.runko;

import ykskakskolme.domain.*;
import ykskakskolme.database.*;
import java.util.*;
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
            String vegaani = "Vegaaniton";
            if (p.vegaaninen()) {
                vegaani = "Vegaaninen";
            }
            //Näytetään pizza
            map.put("pizza", p);
            map.put("vegaani", vegaani);
            return new ModelAndView(map, "pizza");
        }, new ThymeleafTemplateEngine());

        Spark.get("/pizzataytteet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            //Parametri osoitteesta
            Integer pizzaId = Integer.parseInt(req.params(":id"));
            //Haetaan pizza
            Pizza p = pizzaDao.findOne(pizzaId);
            List<Tayte> taytteet = tayteDao.findAll();
            //Näytetään pizza
            map.put("pizza", p);
            map.put("taytteet", taytteet);
            return new ModelAndView(map, "pizzataytteet");
        }, new ThymeleafTemplateEngine());

        Spark.post("/pizzataytteet/:id", (req, res) -> {
            int pizzaId = Integer.parseInt(req.params(":id"));
            int tayteId = Integer.parseInt(req.queryParams("taytebox"));

            pizzaDao.lisaaTayte(pizzaId, tayteId);

            res.redirect("/pizzataytteet/" + pizzaId);
            return "";
        });

        Spark.get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Pohja> pohjat = pohjaDao.findAll();
            List<Kastike> kastikkeet = kastikeDao.findAll();
            List<Tayte> taytteet = tayteDao.findAll();
            List<Koko> koot = kokoDao.findAll();

            map.put("pohjat", pohjat);
            map.put("kastikkeet", kastikkeet);
            map.put("taytteet", taytteet);
            map.put("koot", koot);

            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());

        Spark.get("/taytteet", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Pohja> pohjat = pohjaDao.findAll();
            List<Kastike> kastikkeet = kastikeDao.findAll();
            List<Tayte> taytteet = tayteDao.findAll();
            // poistetaan listalta eipohjaa ja eikastiketta
            pohjat.remove(0);
            kastikkeet.remove(0);

            Pohja eipohjaa = pohjaDao.findOne(1);
            Kastike eikastiketta = kastikeDao.findOne(1);

            map.put("pohjat", pohjat);
            map.put("kastikkeet", kastikkeet);
            map.put("taytteet", taytteet);
            map.put("eipohjaa", eipohjaa);
            map.put("eikastiketta", eikastiketta);

            return new ModelAndView(map, "taytteet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Tilastoalkio> pohjatilasto = pohjaDao.tilasto();
            List<Tilastoalkio> taytetilasto = tayteDao.tilasto();
           
            map.put("pohjatilasto", pohjatilasto);
            map.put("taytetilasto", taytetilasto);

            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaapizza", (req, res) -> {
            String nimi = req.queryParams("pizza");

            System.out.println("Saatiin: "
                    + req.queryParams("pizza") + " "
                    + req.queryParams("kokobox") + " "
                    + req.queryParams("pohjabox") + " "
                    + req.queryParams("kastikebox") + " "
                    + req.queryParams("hinta"));

            int pohjaId = Integer.parseInt(req.queryParams("pohjabox"));
            int kastikeId = Integer.parseInt(req.queryParams("kastikebox"));
            int kokoId = Integer.parseInt(req.queryParams("kokobox"));
            double hinta = Double.parseDouble(req.queryParams("hinta"));

            int id = pizzaDao.saveRaw(nimi, pohjaId, kastikeId, kokoId, hinta);

            res.redirect("/pizzataytteet/" + id);
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

        Spark.post("/poistapohja/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            if (req.params(":id").equals("1")) {
                res.redirect("/taytteet");
                return "";
            }

            pohjaDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/poistakastike/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            if (req.params(":id").equals("1")) {
                res.redirect("/taytteet");
                return "";
            }

            kastikeDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/poistatayte/:id", (req, res) -> {
            System.out.println("Poistetaan: "
                    + req.params(":id"));

            if (req.params(":id").equals("1")) {
                res.redirect("/taytteet");
                return "";
            }

            tayteDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/taytteet");
            return "";
        });
        
    }
    
}
