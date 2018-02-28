package ykskakskolme.runko;

import java.net.URLDecoder;
import java.net.URLEncoder;
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
        
        Spark.post("/search", (req, res) -> {
            String param = req.queryParams("hakuteksti");
            param = URLEncoder.encode(param, "UTF-8");
            res.redirect("/search/" + param);
            return "";
        });
        
        Spark.get("/search/:param", (req, res) -> {
            String param = req.params(":param");
            param = URLDecoder.decode(param, "UTF-8");
            List<Pizza> hakutulos = pizzaDao.findLike(param);
  
            HashMap map = new HashMap<>();

            map.put("pizzat", hakutulos);

            return new ModelAndView(map, "search");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/search/", (req, res) -> {
            List<Pizza> hakutulos = pizzaDao.findAll();
  
            HashMap map = new HashMap<>();

            map.put("pizzat", hakutulos);

            return new ModelAndView(map, "search");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/search", (req, res) -> {
            List<Pizza> hakutulos = pizzaDao.findAll();
  
            HashMap map = new HashMap<>();

            map.put("pizzat", hakutulos);

            return new ModelAndView(map, "search");
        }, new ThymeleafTemplateEngine());

        Spark.get("/pizza/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            //Parametri osoitteesta
            Integer pizzaId = Integer.parseInt(req.params(":id"));
            //Haetaan pizza
            Pizza p = pizzaDao.findOne(pizzaId);
            for (Tayte t: p.getTaytteet()) {
                if (t != null && !t.getOhje().trim().isEmpty()) {
                    t.setOhje(" - " + t.getOhje());
                }
            }
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
            for (Tayte t: p.getTaytteet()) {
                if (t != null && !t.getOhje().trim().isEmpty()) {
                    t.setOhje(" - " + t.getOhje());
                }
            }
            List<Tayte> taytteet = tayteDao.findAll();
            //Näytetään pizza
            map.put("pizza", p);
            map.put("taytteet", taytteet);
            return new ModelAndView(map, "pizzataytteet");
        }, new ThymeleafTemplateEngine());

        Spark.post("/pizzataytteet/:id", (req, res) -> {
            int pizzaId = Integer.parseInt(req.params(":id"));
            int tayteId = Integer.parseInt(req.queryParams("taytebox"));
            String ohje = req.queryParamOrDefault("ohje", "");

            pizzaDao.lisaaTayte(pizzaId, tayteId, ohje);

            res.redirect("/pizzataytteet/" + pizzaId);
            return "";
        });
        
         Spark.post("/pizzataytteet/:pizza/delete/:tayte", (req, res) -> {
            int pizzaId = Integer.parseInt(req.params(":pizza"));
            int tayteId = Integer.parseInt(req.params(":tayte"));

            pizzaDao.poistaTayte(pizzaId, tayteId);

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
            
            map.put("pohjat", pohjat);
            map.put("kastikkeet", kastikkeet);
            map.put("taytteet", taytteet);
            
            return new ModelAndView(map, "taytteet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Tilastoalkio> pohjatilasto = pohjaDao.tilasto();
            List<Tilastoalkio> taytetilasto = tayteDao.tilasto();
            List<Tilastoalkio> kastiketilasto = kastikeDao.tilasto();
           
            map.put("pohjatilasto", pohjatilasto);
            map.put("kastiketilasto", kastiketilasto);
            map.put("taytetilasto", taytetilasto);

            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaapizza", (req, res) -> {
            String nimi = req.queryParams("pizza");
            
            if (emptyInput(req.queryParams("pizza"))) {
                res.redirect("/lisays");
                return "";
            }
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
            
            pizzaDao.lisaaTayte(id, 1, "");
            
            res.redirect("/pizzataytteet/" + id);
            return "";
        });

        Spark.post("/lisaatayte", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("tayte") + " "
                    + req.queryParams("vegaaninen"));
            
            Boolean vegaaninen = req.queryParamOrDefault("vegaaninen", "false").equals("true");
            if (emptyInput(req.queryParams("tayte"))) {
                res.redirect("/taytteet");
                return "";
            }
            Tayte t = new Tayte(null, req.queryParams("tayte"), vegaaninen);
            tayteDao.saveOrUpdate(t);

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/lisaapohja", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("pohja"));
            
            if (emptyInput(req.queryParams("pohja"))) {
                res.redirect("/taytteet");
                return "";
            }
            Pohja p = new Pohja(null, req.queryParams("pohja"));
            pohjaDao.saveOrUpdate(p);

            res.redirect("/taytteet");
            return "";
        });

        Spark.post("/lisaakastike", (req, res) -> {
            System.out.println("Saatiin: "
                    + req.queryParams("kastike"));
            
            if (emptyInput(req.queryParams("kastike"))) {
                res.redirect("/taytteet");
                return "";
            }
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
    
    public static boolean emptyInput(String input) {
        return input.trim().isEmpty();
    }
    
}
