package ykskakskolme.domain;

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
    
    public String getFormatNimi() {
        String ret = nimi;
        if (vegaaninen) {
            ret += " (v)";
        }
        return ret;
    }
    
    public void setNoDelete() {
        this.id = -1;
    }
}
