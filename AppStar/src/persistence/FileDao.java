package persistence;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import static persistence.Connessione.CONN;

public class FileDao {

    public static void importaFile(File file, String relazione) throws SQLException {

        Connessione.connettiti();
        String importazione = "COPY " + relazione +" FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";
        //Si e notato che il path del file da importare non deve contenere \Desktop\.
        //Ho ulteriormente notato che PostgreSQL mi legge i file in formato csv solamente se li prendo da
        //disco locale C.

        try {
            PreparedStatement ps1 = CONN.prepareStatement("DELETE FROM " + relazione);
            ps1.executeUpdate();

            //CONN.setReadOnly(true);
            PreparedStatement ps2 = CONN.prepareStatement(importazione);
            ps2.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }

    public static void inserisciFileDatiSatellite(String nomeAgenzia, String nomeSatellite, LocalDate inizio,
                                                  Period durata) throws SQLException{
        Connessione.connettiti();

        String insertSatelliti;

        if (durata==null){
            insertSatelliti =    "INSERT INTO satelliti(\"NAME_SAT\", \"DATA_INIZIO\", \"DURATA\") VALUES" +
                    "('" + nomeSatellite + "', '" + inizio + "', " + durata + ")";
        }else {
            insertSatelliti =    "INSERT INTO satelliti(\"NAME_SAT\", \"DATA_INIZIO\", \"DURATA\") VALUES" +
                    "('" + nomeSatellite + "', '" + inizio + "', '" + durata + "')";
        }

        String insertAgenzie =      "INSERT INTO agenzie(\"NAME_AG\", \"SAT\") VALUES" +
                                                "('" + nomeAgenzia + "', '" + nomeSatellite + "')";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(insertSatelliti);
            PreparedStatement ps2 = CONN.prepareStatement(insertAgenzie);
            ps1.executeUpdate(); ps2.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }

    public static void inserisciDatiStrumento(float banda, String strumento) throws SQLException{

        Connessione.connettiti();

        String insertBanda =    "INSERT INTO bande(\"WAVE_LENGTH\", \"STRUMENTO\") VALUES " +
                                            "('" + banda + "', '" + strumento + "')";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(insertBanda);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }

    //TODO: Come devono essere distribuiti i dati all'interno del nostro DataBase ?
    public void distribuisciDati(){
        //Distrubuisce i dati contenuti negli imp nel DB di AppStar.

        /*Prerequisiti:
        * 1) aver fatto gli import dei csv;
        * 2) aver inserito dati satelliti.*/

        Connessione.connettiti();

        riempiFilamenti();
        riempiSegmenti();
        //riempiStelle(); NO!!! E identica a stelle_imp sul DB di rossi.
        riempiVisibilita();
        //riempiMisurazione();
        //riempiSegmenti();
        //riempiPuntiContorni();
        //riempiPuntiSegmenti();
        //riempiContorni();
    }

    private void riempiFilamenti(){
        //Riempe la tabella "filamenti".

        String fillQuery = "INSERT INTO filamenti VALUES(SELECT \"IDFIL\", \"NAME\" " +
                                                        "FROM filamenti_imp" +
                                                        "WHERE (\"IDFIL\",\"NAME\") NOT IN (    SELECT \"IDFIL\", \"NAME\"" +
                                                                                                "FROM filamenti))";

        String updateQuery1 = "UPDATE filamenti f SET \"NUMSEG\" = (SELECT COUNT(*) " +
                                                                    "FROM scheletri_imp si " +
                                                                    "WHERE (f.\"IDFIL\" = si.\"IDFIL\" AND" +
                                                                    " \"N\" = 1))";
        try {
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
            PreparedStatement ps2 = CONN.prepareStatement(updateQuery1);
            ps2.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiSegmenti(){
        //Riempe la tabella "segmenti".

        String fillQuery = "INSERT INTO segmenti (  SELECT \"IDBRANCH\", \"TYPE\", \"IDFIL\"" +
                                                    "FROM scheletri_imp" +
                                                    "WHERE \"N\" = 1)";

        String updateQuery1 =   "UPDATE segmenti" +
                                "SET \"NAME_FIL\" = (   SELECT \"NAME\"" +
                                                        "FROM filamenti_imp f" +
                                                        "WHERE f.\"IDFIL\" = segmenti.\"IDFIL\")";

        try {
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
            PreparedStatement ps2 = CONN.prepareStatement(updateQuery1);
            ps2.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiVisibilita(){
        //Riempie la tabella "visibilita".

        String fillQuery =  "INSERT INTO visibilita VALUES (    SELECT \"IDSTAR\"" +
                                                                "FROM stelle_imp)";

        String updateQuery1 =  "UPDATE visibilita" +
                                "SET \"STRUMENTO\" = (  SELECT \"STRUMENTO\"" +
                                                        "FROM strumenti)";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
            PreparedStatement ps2 = CONN.prepareStatement(updateQuery1);
            ps2.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}