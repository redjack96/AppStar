package entity;

public class Filamento {
    private String id;
    private String nome;
    private int numSeg;
    private String satellite;

    public Filamento(String id, String nome, int numSeg, String satellite){
        this.id = id;
        this.nome = nome;
        this.numSeg = numSeg;
        this.satellite = satellite;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getNumSeg() {
        return numSeg;
    }
    public void setNumSeg(int numSeg) {
        this.numSeg = numSeg;
    }
    public String getSatellite() {
        return satellite;
    }
    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }
}
