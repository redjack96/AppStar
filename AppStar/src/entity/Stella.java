package entity;

public class Stella{
    private int idStar;
    private String nameStar;
    private float glon;
    private float glat;
    private float flux;
    private String type;

    public Stella(int idStar, String nameStar, float glon, float glat, float flux, String type){
        this.idStar = idStar;
        this.nameStar = nameStar;
        this.glon = glon;
        this.glat = glat;
        this.flux = flux;
        this.type = type;
    }

    public int getIdStar() {
        return idStar;
    }
    public void setIdStar(int idStar) {
        this.idStar = idStar;
    }
    public String getNameStar() {
        return nameStar;
    }
    public void setNameStar(String nameStar) {
        this.nameStar = nameStar;
    }
    public float getGlon() {
        return glon;
    }
    public void setGlon(float glon) {
        this.glon = glon;
    }
    public float getGlat() {
        return glat;
    }
    public void setGlat(float glat) {
        this.glat = glat;
    }
    public float getFlux() {
        return flux;
    }
    public void setFlux(float flux) {
        this.flux = flux;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
