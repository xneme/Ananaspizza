package ykskakskolme.domain;

public class Pohja {
    
    private Integer id;
    private String nimi;

    public Pohja(Integer id, String nimi) {
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
