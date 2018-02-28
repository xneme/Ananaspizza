package ykskakskolme.domain;

public class Tayte {

    private Integer id;
    private String nimi;
    private Boolean vegaaninen;
    private String ohje;

    public Tayte(Integer id, String nimi, Boolean vegaaninen) {
        this.id = id;
        this.nimi = nimi;
        this.vegaaninen = vegaaninen;
        this.ohje = "";
    }

    public Tayte(Integer id, String nimi, Boolean vegaaninen, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.vegaaninen = vegaaninen;
        this.ohje = ohje;
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
        String ret = nimi;
        if (vegaaninen) {
            ret += " (v)";
        }
        return ret;
    }
    
    public void setNoDelete() {
        this.id = -1;
    }

    public String getOhje() {
        return ohje;
    }
    
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    
}
