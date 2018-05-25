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
    public void setAmministratore(boolean amministratore){ this.amministratore = amministratore; }
    public ArrayList<String> getInfo(){
        ArrayList<String> info = new ArrayList<>(6);
        String admin = "";
        if (this.isAmministratore()){
            admin = "amministratore";
        }else if(!this.isAmministratore()){
            admin = "notAmministratore";
        }
        info.add(this.nome); info.add(this.cognome); info.add(this.userID); info.add(this.password);
        info.add(this.email); info.add(admin);
        return info;
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

    public ArrayList<Integer> cercaFilamenti(ObservableList<Filamento> filamento, TableView tableView, TableColumn id,
                                             TableColumn nome, TableColumn numSeg, TableColumn satellite, TableColumn con,
                                             TableColumn ell, float lum, float ellipt1, float ellipt2, int pagina){

        ArrayList<Integer> result = new ArrayList<>(2);

        try{
            result = FileDao.cercaFilamenti(filamento, tableView, id, nome, numSeg, satellite, con, ell, lum, ellipt1,
                    ellipt2, pagina);
        }catch (SQLException e){
            result.add(0,0); result.add(1, 0);
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int cercaFilamentiSeg(ObservableList<Filamento> filamento, TableView tableView, TableColumn id,
                                 TableColumn nome, TableColumn satellite, TableColumn numSeg, int seg1, int seg2,
                                 int pagina){

        int result;

        try{
            result = FileDao.cercaFilamentiSeg(filamento, tableView, id, nome, satellite, numSeg, seg1, seg2, pagina);
        }catch (SQLException e){
            result = 0;
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void cercaInRegione(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn
            nome, TableColumn satellite, TableColumn numSeg, float lungh, float centLon, float
                                       centLat, boolean geom, int pagina){

        try{
            FileDao.cercaInRegione(filamento, tableView, id, nome, satellite, numSeg, lungh, centLon, centLat, geom,
                    pagina);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> cercaInFilamento(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                             TableColumn nameStar, TableColumn glon, TableColumn glat,
                                             TableColumn flux, TableColumn type, int idFil, String satellite,
                                             int pagina){

        ArrayList<Integer> arrayList = new ArrayList<>(3);

        try{
            arrayList = FileDao.cercaInFilamento(stella, tableView, id, nameStar, glon, glat, flux, type, idFil,
                    satellite, pagina);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return arrayList;
    }

    public ArrayList<Integer> cercaInRegione(ObservableList<Stella> stella, TableView tableView, TableColumn id,
                                             TableColumn nameStar, TableColumn glon, TableColumn glat,
                                             TableColumn flux, TableColumn type, float h, float b, float lon,
                                             float lat, int pagina){
        ArrayList<Integer> arrayList = new ArrayList<>(6);

        try{
            arrayList = FileDao.cercaInRegione(stella, tableView, id, nameStar, glon, glat, flux, type, h, b, lon, lat,
                    pagina);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<Float> calcolaDistanzeSegCon(int idSeg, int idFil, String satellite){
        ArrayList<Float> distanze = new ArrayList<>(2);
        try{
            distanze = FileDao.calcolaDistSegCon(idSeg, idFil, satellite);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return distanze;
    }

    public ArrayList<Integer> trovaSegmenti(int idFil, String satellite){
        ArrayList<Integer> lista = null;
        try {
            lista = FileDao.trovaSegmenti(idFil, satellite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void calcolaDistanzeStellaSpina(ObservableList<StellaSpina> listaStelle, TableView tableView, TableColumn id,
                                           TableColumn nameStar, TableColumn glon, TableColumn glat,
                                           TableColumn flux, TableColumn type, TableColumn distanza, int idFil,
                                           String satellite, String ord, int pagina){
        try{
            FileDao.calcolaDistStellaSpina(listaStelle, tableView, id, nameStar, glon, glat, flux, type, distanza, idFil,
                    satellite, ord, pagina);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
