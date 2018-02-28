/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ykskakskolme.domain;

import ykskakskolme.domain.Koko;
import ykskakskolme.domain.Kastike;
import java.util.ArrayList;
import java.util.List;


public class Pizza {
    //TODO lisää sisältöä pizzaan
    private Integer id;
    private String nimi;
    private Pohja pohja;
    private Kastike kastike;
    private List<Tayte> taytteet;
    private Koko koko;
    private Double hinta;

    public Pizza(Integer id, String nimi, Pohja pohja, Kastike kastike, List<Tayte> taytteet, Koko koko, Double hinta) {
        this.id = id;
        this.nimi = nimi;
        this.pohja = pohja;
        this.kastike = kastike;
        this.taytteet = taytteet;
        this.koko = koko;
        this.hinta = hinta;
    }

    public Pizza(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.pohja = null;
        this.kastike = null;
        this.taytteet = new ArrayList<>();
        this.koko = null;
        this.hinta = 4.90;
    }
    
    public boolean vegaaninen() {
        return taytteet.stream().allMatch(t -> t.getVegaaninen()) && pohja.getVegaaninen() && kastike.getVegaaninen();
    }
    public Double getHinta() {
        return hinta;
    }
    
    public String getNimi() {
        return nimi;
    }

    public Integer getId() {
        return id;
    }

    public Pohja getPohja() {
        return pohja;
    }

    public List<Tayte> getTaytteet() {
        return taytteet;
    }

    public Kastike getKastike() {
        return kastike;
    }

    public Koko getKoko() {
        return koko;
    }
    
    
}
