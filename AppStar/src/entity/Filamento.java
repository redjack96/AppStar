package entity;

import java.math.BigDecimal;

public class Filamento {
    private String id;
    private String nome;
    private int numSeg;
    private String satellite;
    private BigDecimal con;
    private BigDecimal ell;

    public Filamento(String id, String nome, int numSeg, String satellite, BigDecimal con, BigDecimal ell){
        this.id = id;
        this.nome = nome;
        this.numSeg = numSeg;
        this.satellite = satellite;
        this.con = con;
        this.ell = ell;
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
    public BigDecimal getCon() {
        return con;
    }
    public void setCon(BigDecimal con) {
        this.con = con;
    }
    public BigDecimal getEll() {
        return ell;
    }
    public void setEll(BigDecimal ell) {
        this.ell = ell;
    }
}
