package com.ykskakskolme.pizzatietokanta;

public class Tayte {

    private Integer id;
    private String nimi;
    private Boolean vegaaninen;

    public Tayte(Integer id, String nimi, Boolean vegaaninen) {
        this.id = id;
        this.nimi = nimi;
        this.vegaaninen = vegaaninen;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public Boolean getVegaaninen() {
        return vegaaninen;
    }
    
}