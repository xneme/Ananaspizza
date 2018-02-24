package com.ykskakskolme.pizzatietokanta;

public class Kastike {

    private Integer id;
    private String nimi;

    public Kastike(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }
    
}
