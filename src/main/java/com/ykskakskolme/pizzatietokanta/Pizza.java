/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykskakskolme.pizzatietokanta;


public class Pizza {
    //TODO lisää sisältöä pizzaan
    private Integer id;
    private String nimi;

    public Pizza(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public Integer getId() {
        return id;
    }
    
    
    
    
    
}
