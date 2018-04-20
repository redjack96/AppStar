package entity;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import persistence.FileDao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteRegistrato {
    private String nome;
    private String cognome;
    private String userID;
    private String password;
    private String email;
    private boolean amministratore = false; //'false': l'utente non e' un amministratore.

    public UtenteRegistrato(String nome, String cognome, String userID, String password, String email){
        //Costruttore di UtenteRegistrato.
        this.nome = nome;
        this.cognome = cognome;
        this.userID = userID;
        this.password = password;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isAmministratore() {
        return amministratore;
    }
    protected void setAmministratore(boolean amministratore){
        this.amministratore = amministratore;
    }

    public ArrayList calcolaCentroide(String nomeFil, int idFil, String satellite){
        ArrayList centroide = new ArrayList<>(2);
        try{
            centroide = FileDao.calcolaCentroide(nomeFil, idFil, satellite);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return centroide;
    }

    public ArrayList calcolaEstensione(String nomeFil, int idFil, String satellite){
        ArrayList estensione = new ArrayList<>(2);
        try{
            estensione = FileDao.calcolaEstensione(nomeFil, idFil, satellite);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return estensione;
    }
    public int calcolaNumSeg(String nomeFil, int idFil, String satellite){
        int num_seg;
        try{
            num_seg = FileDao.calcolaNumSeg(nomeFil, idFil, satellite);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            num_seg = -1;
        }
        return num_seg;
    }

    public void cercaFilamenti(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn nome,
                               TableColumn numSeg, TableColumn satellite, BigDecimal lum, BigDecimal ellipt1,
                               BigDecimal ellipt2, String simbolo, int pagina){
        try{
            FileDao.cercaFilamenti(filamento, tableView, id, nome, numSeg, satellite, lum, ellipt1, ellipt2, simbolo, pagina);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
