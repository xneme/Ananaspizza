package ykskakskolme.domain;

public class Pohja {
    
    private Integer id;
    private String nimi;
    private Boolean vegaaninen;

    public Pohja(Integer id, String nimi, Boolean vegaaninen) {
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
    
    public String getNimiVeg() {
        if (vegaaninen) {
            return nimi + " (v)";
        }
        return nimi;
    }
}
