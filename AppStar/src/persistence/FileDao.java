package persistence;

import entity.Filamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.Connessione.CONN;

public class FileDao {

    public static ArrayList calcolaCentroide(String nomeFil, int idFil, String satellite) throws SQLException{
        //Calcola la posizione del centroide del filamento specificato.

        ArrayList<String> centroide = new ArrayList<>(2);

        Connessione.connettiti();
        String query1 = "SELECT AVG(p.\"GLON_CONT\") AS lon_centroide, AVG(p.\"GLAT_CONT\") AS lat_centroide\n" +
                "FROM punti_contorni p JOIN contorni c ON p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\"\n" +
                "WHERE c.\"NAME_FIL\" = ?";

        String query2 = "SELECT AVG(p.\"GLON_CONT\") AS lon_centroide, AVG(p.\"GLAT_CONT\") AS lat_centroide\n" +
                "FROM punti_contorni p JOIN contorni c ON p.\"N\" = c.\"NPCONT\" " +
                "JOIN filamenti f ON f.\"NAME\" = c.\"NAME_FIL\" AND f.\"SATELLITE\" = p.\"SATELLITE\"" +
                "WHERE f.\"IDFIL\" = ? AND p.\"SATELLITE\" = ?";

        try{
            PreparedStatement ps1;
            if (!nomeFil.equals("")){
                ps1 = CONN.prepareStatement(query1);
                ps1.setString(1, nomeFil);

            }else{
                ps1 = CONN.prepareStatement(query2);
                ps1.setInt(1, idFil);
                ps1.setString(2, satellite);

            }
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            BigDecimal lon_centroide = rs1.getBigDecimal(1);
            BigDecimal lat_centroide = rs1.getBigDecimal(2);
            centroide.add(0, lon_centroide.toString());
            centroide.add(1, lat_centroide.toString());
            System.out.println("Centroide calcolato correttamente");
            return centroide;
        }catch (SQLException | NullPointerException | java.lang.IndexOutOfBoundsException e){
            ArrayList<String> errore = new ArrayList<>(2);
            errore.add(0, "NON TROVATO");
            errore.add(1, "NON TROVATO");
            System.out.println("Centroide non trovato");
            return errore;
        } finally{
            CONN.close();
        }
    }

    public static ArrayList calcolaEstensione(String nomeFil, int idFil, String satellite) throws SQLException{
        //Calcola l'estensione del filamento specificato.
        ArrayList<String> estensione = new ArrayList<>(2);

        Connessione.connettiti();
        String query1 = "SELECT max(\"GLON_CONT\")-min(\"GLON_CONT\") AS est_lon, max(\"GLAT_CONT\")-min(\"GLAT_CONT\") AS est_lat\n" +
                "FROM filamenti f JOIN contorni c ON f.\"NAME\" = c.\"NAME_FIL\" JOIN punti_contorni p ON c.\"NPCONT\" = p.\"N\" \n" +
                "WHERE \"NAME\" = ? OR (f.\"IDFIL\" = ? AND f.\"SATELLITE\" = ?)";

        try{
            PreparedStatement ps1;
            ps1 = CONN.prepareStatement(query1);
            ps1.setString(1, nomeFil);
            if (idFil>=0) ps1.setInt(2, idFil); else ps1.setInt(2, 0);
            ps1.setString(3, satellite);

            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            BigDecimal lon_centroide = rs1.getBigDecimal(1);
            BigDecimal lat_centroide = rs1.getBigDecimal(2);
            estensione.add(0, lon_centroide.toString());
            estensione.add(1, lat_centroide.toString());
            System.out.println("Estensione calcolata correttamente");
            return estensione;
        }catch (SQLException | NullPointerException | java.lang.IndexOutOfBoundsException e){
            ArrayList<String> errore = new ArrayList<>(2);
            errore.add(0, "NON TROVATO");
            errore.add(1, "NON TROVATO");
            System.out.println("Estensione non trovata");
            return errore;
        } finally{
            CONN.close();
        }
    }

    public static int calcolaNumSeg(String nomeFil, int idFil, String satellite) throws SQLException{
        //Calcola l'estensione del filamento specificato.
        int num_seg;

        Connessione.connettiti();
        String query1 = "SELECT \"NUM_SEG\"\n" +
                "FROM filamenti\n" +
                "WHERE (\"IDFIL\" = ? AND \"SATELLITE\" = ?) OR \"NAME\" = ?";

        try{
            PreparedStatement ps1;
            ps1 = CONN.prepareStatement(query1);
            if (idFil>=0) ps1.setInt(1, idFil); else ps1.setInt(1, 0);
            ps1.setString(2, satellite);
            ps1.setString(3, nomeFil);

            ResultSet rs1 = ps1.executeQuery();
            rs1.next();

            num_seg = rs1.getInt(1);

            System.out.println("Numero di segmenti calcolato correttamente");
            return num_seg;
        }catch (SQLException | NullPointerException | java.lang.IndexOutOfBoundsException  e){
            System.out.println(e.getMessage());
            System.out.println("Numero di segmenti non trovato");
            return -1;
        } finally{
            CONN.close();
        }
    }

    public static void cercaFilamenti(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn nome,
                                      TableColumn numSeg, TableColumn satellite, BigDecimal lum, BigDecimal ellipt1,
                                      BigDecimal ellipt2, String simbolo, int pagina) throws SQLException{

        Connessione.connettiti();

        BigDecimal _1 = new BigDecimal(1);
        BigDecimal _100 = new BigDecimal(100);
        //BigDecimal contrasto = _100.multiply(lum.subtract(_1));
        BigDecimal contrasto2 = _1.add(lum.divide(_100,1, RoundingMode.DOWN)); // questa dovrebbe essere quella corretta

        String clausolaWHERE = "";

        if (simbolo.equals("uguale")){
            clausolaWHERE = "WHERE m.\"CONTRAST\" = '" + contrasto2 + "'";
        }else if (simbolo.equals("minore")){
            clausolaWHERE = "WHERE m.\"CONTRAST\" < '" + contrasto2 + "'";
        }else if (simbolo.equals("maggiore")){
            clausolaWHERE = "WHERE m.\"CONTRAST\" > '" + contrasto2 + "'";
        }

        int offset = pagina * 20;

        String query =  "SELECT * " +
                        "FROM filamenti f JOIN misurazione m ON f.\"NAME\" = m.\"FILAMENTO\" " +
                        clausolaWHERE + " AND m.\"ELLIPTICITY\" > '" + ellipt1 +
                                        "' AND m.\"ELLIPTICITY\" < '" + ellipt2 + "' " +
                        "ORDER BY \"SATELLITE\" ASC, \"IDFIL\" ASC LIMIT 20 OFFSET ?";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(query);
            ps1.setInt(1, offset);
            //lista di
            filamento = FXCollections.observableArrayList();
            ResultSet rs = ps1.executeQuery();
            while (rs.next()){
                Filamento tuplaFilamenti = new Filamento(rs.getString("IDFIL"), rs.getString("NAME"),
                        rs.getInt("NUM_SEG"), rs.getString("SATELLITE"));
                filamento.add(tuplaFilamenti);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally{
            CONN.close();
        }
        // setta i nomi delle colonne
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        numSeg.setCellValueFactory(new PropertyValueFactory<>("numSeg"));
        satellite.setCellValueFactory(new PropertyValueFactory<>("satellite"));
        //svuota la tableview
        tableView.setItems(null);
        //riempie la tableview con massimo 20 dei filamenti trovati
        tableView.setItems(filamento);
    }
}




