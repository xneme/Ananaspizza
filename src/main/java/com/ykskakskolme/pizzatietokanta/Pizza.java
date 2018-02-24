/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykskakskolme.pizzatietokanta;

import java.util.ArrayList;
import java.util.List;


public class Pizza {
    //TODO lisää sisältöä pizzaan
    private Integer id;
    private String nimi;
    private List<Pohja> pohjat;
    private List<Tayte> taytteet;
    private Double hinta;

    public Pizza(Integer id, String nimi, List<Pohja> pohjat, List<Tayte> taytteet, Double hinta) {
        this.id = id;
        this.nimi = nimi;
        this.pohjat = pohjat;
        this.taytteet = taytteet;
        this.hinta = hinta;
    }

    public Pizza(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.pohjat = new ArrayList<>();
        this.taytteet = new ArrayList<>();
        this.hinta = 4.90;
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

    public List<Pohja> getPohjat() {
        return pohjat;
    }

    public List<Tayte> getTaytteet() {
        return taytteet;
    }
    
}
