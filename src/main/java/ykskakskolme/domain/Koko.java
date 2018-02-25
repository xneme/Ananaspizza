package ykskakskolme.domain;

public class Koko {

    private Integer id;
    private String nimi;

    public Koko(Integer id, String nimi) {
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
