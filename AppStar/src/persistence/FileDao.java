package persistence;

import entity.Filamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static ArrayList<Integer> cercaFilamenti(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn nome,
                                      TableColumn numSeg, TableColumn satellite, TableColumn con, TableColumn ell,
                                      float lum, float ellipt1, float ellipt2, int pagina)
            throws SQLException, NumberFormatException{

        Connessione.connettiti();

        int numRic;
        int tot;
        ArrayList<Integer> result = new ArrayList<>(2);

        /*BigDecimal _1 = new BigDecimal(1);
        BigDecimal _100 = new BigDecimal(100);
        BigDecimal contrasto = lum.divide(_100).add(_1);*/

        double contrasto = 1.0 + (lum/100.0);

        int offset = (pagina - 1) * 20;

        String query =  "SELECT f.\"IDFIL\", f.\"NAME\", f.\"NUM_SEG\", f.\"SATELLITE\", m.\"CONTRAST\", m.\"ELLIPTICITY\" " +
                        "FROM filamenti f JOIN misurazione m ON f.\"NAME\" = m.\"FILAMENTO\" " +
                        "WHERE m.\"CONTRAST\" > '" + contrasto + "' AND m.\"ELLIPTICITY\" > '" + ellipt1 +
                                        "' AND m.\"ELLIPTICITY\" < '" + ellipt2 + "' " +
                        "ORDER BY \"SATELLITE\" ASC, \"IDFIL\" ASC LIMIT 20 OFFSET ?";

        String queryCount =     "SELECT COUNT(*) AS \"CONTEGGIO\" " +
                                "FROM filamenti f JOIN misurazione m ON f.\"NAME\" = m.\"FILAMENTO\" "+
                                "WHERE m.\"CONTRAST\" > '" + contrasto + "' AND m.\"ELLIPTICITY\" > '" + ellipt1 +
                                "' AND m.\"ELLIPTICITY\" < '" + ellipt2 + "' ";

        String countAll =   "SELECT COUNT(*) FROM filamenti";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(query);
            ps1.setInt(1, offset);
            filamento = FXCollections.observableArrayList();
            ResultSet rs = ps1.executeQuery();
            while (rs.next()){
                Filamento filamenti = new Filamento(rs.getString("IDFIL"), rs.getString("NAME"),
                        rs.getInt("NUM_SEG"), rs.getString("SATELLITE"),
                        (rs.getBigDecimal("CONTRAST")), (rs.getBigDecimal("ELLIPTICITY")));
                filamento.add(filamenti);
            }

            Statement st2 = CONN.createStatement();
            ResultSet rs2 = st2.executeQuery(queryCount);
            rs2.next();
            numRic = rs2.getInt(1);
            System.out.println("NUMERO RICORRENZE TROVATE: " + numRic);
            Statement st3 = CONN.createStatement();
            ResultSet rs3 = st3.executeQuery(countAll);
            rs3.next();
            tot = rs3.getInt(1);
            result.add(0, numRic); result.add(1, tot);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            result.add(0, 0); result.add(1, 0);
        } finally{
            CONN.close();
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        numSeg.setCellValueFactory(new PropertyValueFactory<>("numSeg"));
        satellite.setCellValueFactory(new PropertyValueFactory<>("satellite"));
        con.setCellValueFactory(new PropertyValueFactory<>("con"));
        ell.setCellValueFactory(new PropertyValueFactory<>("ell"));
        tableView.setItems(null);
        tableView.setItems(filamento);

        return result;
    }

    public static int cercaFilamentiSeg(ObservableList<Filamento> filamento, TableView tableView, TableColumn idColumn,
                                        TableColumn nomeColumn, TableColumn satColumn, TableColumn numSegColumn,
                                        int seg1, int seg2, int pagina) throws SQLException{

        Connessione.connettiti();

        int numRic;
        int offset = (pagina-1) * 20;

        String query = "SELECT * " +
                        "FROM filamenti " +
                        "WHERE \"NUM_SEG\" >= ? AND \"NUM_SEG\" <= ? " +
                        "LIMIT 20 OFFSET ?";

        String queryCount = "SELECT COUNT(*) " +
                            "FROM filamenti " +
                            "WHERE \"NUM_SEG\" >= ? AND \"NUM_SEG\" <= ?";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(query);
            ps1.setInt(1, seg1);
            ps1.setInt(2, seg2);
            ps1.setInt(3, offset);
            filamento = FXCollections.observableArrayList();
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                Filamento filamenti = new Filamento(rs1.getString("IDFIL"), rs1.getString("NAME"),
                        rs1.getInt("NUM_SEG"), rs1.getString("SATELLITE"), null, null);
                filamento.add(filamenti);
            }

            PreparedStatement ps2 = CONN.prepareStatement(queryCount);
            ps2.setInt(1, seg1);
            ps2.setInt(2, seg2);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            numRic = rs2.getInt(1);
            System.out.println("NUMERO RICORRENZE TROVATE: " + numRic);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            numRic = 0;
        } finally{
            CONN.close();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        satColumn.setCellValueFactory(new PropertyValueFactory<>("satellite"));
        numSegColumn.setCellValueFactory(new PropertyValueFactory<>("numSeg"));
        tableView.setItems(null);
        tableView.setItems(filamento);

        return numRic;
    }

    public static void cercaInRegione(ObservableList<Filamento> filamento, TableView tableView, TableColumn id, TableColumn
                               nome, TableColumn satellite, TableColumn numSeg, float lungh, float centLon, float
                               centLat, boolean geom, int pagina) throws SQLException{

        Connessione.connettiti();

        String query;
        int offset = (pagina-1) * 20;

        float maxLat = centLat + lungh/2;
        float minLat = centLat - lungh/2;
        float maxLon = centLon + lungh/2;
        float minLon = centLon - lungh/2;

        String squareQuery = "SELECT *\n" +
                "FROM filamenti \n" +
                "WHERE \"NAME\" NOT IN ( \n" +
                "  SELECT DISTINCT c.\"NAME_FIL\"\n" +
                "  FROM punti_contorni p JOIN contorni c ON (p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\")\n" +
                "  WHERE p.\"GLON_CONT\" < '" + minLon + "' OR \n" +
                "    p.\"GLON_CONT\" > '" + maxLon + "' OR\n" +
                "    p.\"GLAT_CONT\" < '" + minLat + "' OR\n" +
                "    p.\"GLAT_CONT\" > '" + maxLat + "')\n" +
                "ORDER BY \"IDFIL\"\n" +
                "LIMIT 20 OFFSET '" + offset + "';";

        String circleQuery = "SELECT *\n" +
                "FROM filamenti\n" +
                "WHERE \"NAME\" NOT IN (\n" +
                "SELECT DISTINCT c.\"NAME_FIL\"\n" +
                "FROM punti_contorni p JOIN contorni c ON (p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\")\n" +
                "WHERE sqrt((p.\"GLON_CONT\"- '" + centLon + "')^2 + (p.\"GLAT_CONT\" + '" + centLat + "')^2) > '" + lungh + "') \n" +
                "ORDER BY \"IDFIL\" \n" +
                "LIMIT 20 OFFSET '" + offset + "';";

        if (geom){                  //true = quadrato
            query = squareQuery;
        } else {                    //false = cerchio
            query = circleQuery;
        }
        try{
            PreparedStatement ps1 = CONN.prepareStatement(query);
            filamento = FXCollections.observableArrayList();
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                Filamento filamenti = new Filamento(rs1.getString("IDFIL"), rs1.getString("NAME"),
                        rs1.getInt("NUM_SEG"), rs1.getString("SATELLITE"), null, null);
                filamento.add(filamenti);
            }
        }catch (SQLException e){
        System.out.println(e.getMessage());
        } finally{
            CONN.close();
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        satellite.setCellValueFactory(new PropertyValueFactory<>("satellite"));
        numSeg.setCellValueFactory(new PropertyValueFactory<>("numSeg"));
        tableView.setItems(null);
        tableView.setItems(filamento);
    }
}




