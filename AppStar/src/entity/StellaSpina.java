package entity;

public class StellaSpina extends Stella {
    private float distanza;

    public StellaSpina(int idStar, String nameStar, float glon, float glat, float flux, String type, float distanza){
        super(idStar, nameStar, glon, glat, flux, type);
        this.distanza = distanza;
    }

    public float getDistanza() {
        return distanza;
    }
    public void setDistanza(float distanza) {
        this.distanza = distanza;
    }
}
