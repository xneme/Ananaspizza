package ykskakskolme.domain;

public class Tilastoalkio {

    private String nimi;
    private Integer maara;

    public Tilastoalkio(String nimi, Integer maara) {
        this.nimi = nimi;
        this.maara = maara;
    }

    public String getNimi() {
        return nimi;
    }

    public Integer getMaara() {
        return maara;
    }

}
