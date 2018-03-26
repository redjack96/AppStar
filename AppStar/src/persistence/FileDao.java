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
    public void distribuisciDati(String satellite, String relazione){
        //Distrubuisce i dati contenuti negli imp nel DB di AppStar (la prima volta).

        /*Prerequisiti:
        * 1) aver fatto gli import dei csv;
        * 2) aver inserito dati satelliti.*/

        Connessione.connettiti();

        /*if (relazione.equals("CONTORNI")){
            riempiPuntiContorni(); //TODO: cambiare la fk.
            riempiContorni();
        }else if (relazione.equals("FILAMENTI")){
            riempiFilamenti();
            riempiMisurazione();
            riempiSegmenti();
            riempiContorni();
        }else if (relazione.equals("SCHELETRI")){
            riempiFilamenti();
            riempiSegmenti();
            riempiPuntiSegmenti();
        }*/

        riempiFilamenti();
        riempiStelle();
        riempiVisibilita(satellite);
        riempiMisurazione();
        riempiSegmenti();
        riempiPuntiContorni();
        riempiPuntiSegmenti();
        riempiContorni();
    }

    private void riempiFilamenti(){
        //Riempe la tabella "filamenti".

        String fillQuery = "INSERT INTO filamenti (SELECT \"IDFIL\", \"NAME\" " +
                                                        "FROM filamenti_imp " +
                                                        "WHERE (\"IDFIL\",\"NAME\") NOT IN (    SELECT \"IDFIL\", \"NAME\"" +
                                                                                                "FROM filamenti))";

        String updateQuery1 = "UPDATE filamenti f SET \"NUM_SEG\" = (SELECT COUNT(*) " +
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

    private void riempiStelle(){
        //Riempie la tabella "stelle".

        String fillQuery =  "INSERT INTO stelle  (    SELECT \"IDSTAR\", \"NAME_STAR\", \"GLON_ST\", \"GLAT_ST\", \"FLUX\", \"TYPE\"" +
                                                            "FROM stelle_imp " +
                                                            "WHERE \"IDSTAR\" NOT IN (  SELECT \"IDSTAR\"" +
                                                                                        "FROM stelle))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiVisibilita(String satellite){
        //Riempie la tabella "visibilita".

        String fillQuery =  "INSERT INTO visibilita  (    SELECT \"IDSTAR\", '" + satellite + "'" +
                                                                "FROM stelle_imp " +
                                                                "WHERE \"IDSTAR\" NOT IN (  SELECT \"STELLA\"" +
                                                                                            "FROM visibilita)";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiMisurazione(){
        //Riempie la tabella "misurazione".

        String fillQuery =  "INSERT INTO misurazione  (   SELECT \"STRUMENTO\", \"FILAMENTO\", \"MEAN_DENS\", \"MEAN_TEMP\", \"ELLIPTICITY\", \"CONTRAST\", \"TOTAL_FLUX\" " +
                                                                "FROM filamenti_imp " +
                                                                "WHERE (\"INSTRUMENT\", \"NAME\") NOT IN (    SELECT \"STRUMENTO\", \"FILAMENTO\" " +
                                                                                                            "FROM misurazione))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiSegmenti(){
        //Riempie la tabella "segmenti".

        String fillQuery =  "INSERT INTO segmenti ( SELECT si.\"IDBRANCH\", si.\"TYPE\", fi.\"IDFIL\"" +
                                                    "FROM scheletri_imp si JOIN filamenti_imp fi ON si.\"IDFIL\" = fi.\"IDFIL\"" +
                                                    "WHERE (si.\"N\" = 1) AND (si.\"IDBRANCH\" NOT IN ( SELECT \"IDBRANCH\"" +
                                                                                                        "FROM segmenti)))";

        /*String fillQuery = "INSERT INTO segmenti (  SELECT \"IDBRANCH\", \"TYPE\"" +
                "FROM scheletri_imp" +
                "WHERE \"N\" = 1 AND \"IDBRANCH\" NOT IN (  SELECT \"IDBRANCH\"" +
                "FROM segmenti)";*/

        /*String updateQuery1 =   "UPDATE segmenti s" +
                "SET s.\"NAME_FIL\" = (   SELECT fi.\"NAME\"" +
                "FROM scheletri_imp si JOIN filamenti_imp fi ON si.\"IDFIL\" = fi.\"IDFIL\"" +
                "WHERE s.\"IDBRANCH\" = si.\"IDFIL\")";*/

        try {
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
            /*PreparedStatement ps2 = CONN.prepareStatement(updateQuery1);
            ps2.executeUpdate();*/
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiPuntiContorni(){
        //Riempie la relazione 'punti_contorni'.

        String fillQuery =  "INSERT INTO punti_contorni  (    SELECT \"GLON_CONT\", \"GLAT_CONT\"" +
                                                                    "FROM contorni_imp " +
                                                                    "WHERE (\"GLON_CONT\", \"GLAT_CONT\") NOT IN (    SELECT \"GLON_CONT\", \"GLAT_CONT\"" +
                                                                                                                    "FROM punti_contorni))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiPuntiSegmenti() {
        //Riempie la relazione 'punti_segmenti'.

        String fillQuery =      "INSERT INTO punti_segmenti  ( SELECT \"IDBRANCH\", \"GLON_BR\", \"GLAT_BR\"" +
                                                                    "FROM scheletri_imp " +
                                                                    "WHERE \"IDBRANCH\" IN (    SELECT  \"IDBRANCH\"" +
                                                                                                "FROM segmenti )" +
                                                                        "AND \"IDBRANCH\" NOT IN (  SELECT \"SEGMENTO\"" +
                                                                                                    "FROM punti_segmenti))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiContorni(){
        //Rimepie la relazione 'contorni'.

        String fillQuery =  "INSERT INTO contorni  (  SELECT fi.\"NAME\", ci.\"GLON_CONT\", ci.\"GLAT_CONT\"" +
                                                            "FROM filamenti_imp fi JOIN contorni_imp ci ON fi.\"IDFIL\" = ci.\"IDFIL\"" +
                                                            "WHERE (fi.\"NAME\", ci.\"GLON_CONT\", ci.\"GLAT_CONT\") NOT IN ( SELECT \"NAME_FIL\", \"GLON_CONT\", \"GLAT_CONT\"" +
                                                                                                                            "FROM contorni))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
