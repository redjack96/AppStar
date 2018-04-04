package persistence;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import static persistence.Connessione.CONN;

public class FileDao {
    // Chiamato da entity.UtenteAmministratore per importare uno dei file csv
    // TODO Cambiare il nome: importaFile e' gia' stato usato in ImportaFileSatelliteController
    public static void importaFile(File file, String relazione) throws SQLException {

        Connessione.connettiti();
        String importazione = "COPY " + relazione +" FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";
        //Si e notato che il path del file da importare non deve contenere \Desktop\.
        //Ho ulteriormente notato che PostgreSQL mi legge i file in formato csv solamente se li prendo da
        //disco locale C.

        try {
            //Tronca la tabella imp temporanea
            PreparedStatement ps1 = CONN.prepareStatement("DELETE FROM " + relazione);
            ps1.executeUpdate();

            PreparedStatement ps2 = CONN.prepareStatement(importazione);
            ps2.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Importazione del file csv nella tabella "+ relazione + " completato!");
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
    //TODO: Prima inserire il nome dello strumento in 'strumenti' poi le bande in 'bande'.
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
    //TODO: satellite viene dal campo testo 'nomeSatellite' di ImportaFileSatelliteGUI
    public void distribuisciDati(String satellite, String relazione){
        //Distrubuisce i dati contenuti negli imp nel DB di AppStar (la prima volta).

        /*Prerequisiti:
        * 1) aver fatto gli import dei csv;
        * 2) aver inserito dati satelliti.*/

        Connessione.connettiti();

        /*if (relazione.equals("CONTORNI")){
            aggiornaPuntiContorni();
        }else if (relazione.equals("FILAMENTI")){
            riempiFilamenti();
            riempiMisurazione(); //RICHIEDE STRUMENTI
            riempiSegmenti();
            riempiContorni(); //RICHIEDE PUNTI_CONTORNI
        }else if (relazione.equals("SCHELETRI")){
            riempiFilamenti_NUM_SEG(); //RICHIEDE FILAMENTI
            riempiSegmenti();
            riempiPuntiSegmenti();
        }else if (relazione.equals("STELLE"){
            riempiStelle();
            riempiVisibilita(satellite); //RICHIEDE SATELLITI e che il satellite sia specificato nel campo testo
            //TODO: sostituire il campo testo NomeSatellite con una choiceBox che contiene i satelliti gia' inseriti
        }*/
        //riempi Utenti, satelliti, agenzie, strumenti e bande tramite questa applicazione, poi...
        riempiStelle();
        riempiVisibilita(satellite);
        riempiFilamenti(); // NUM_SEG = null
        riempiMisurazione();
        riempiSegmenti();
        //TODO: riempiFilamenti_NUM_SEG()
        riempiPuntiSegmenti();
        riempiPuntiContorni();
        riempiContorni();
    }

    private void riempiFilamenti(){
        // OK riusabile. Riempe la tabella "filamenti".

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
        //OK riusabile. Riempie la tabella "stelle".

        String fillQuery =  "INSERT INTO stelle  (    SELECT *" +
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
        //OK riusabile. Riempie la tabella "visibilita". Il satellite e' scelto nell'applicazione

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
        //OK riusabile. Riempie la tabella "misurazione".

        String fillQuery =  "INSERT INTO misurazione (SELECT \"INSTRUMENT\", \"NAME\", \"MEAN_DENS\", \"MEAN_TEMP\", \"ELLIPTICITY\", \"CONTRAST\", \"TOTAL_FLUX\" \n" +
                                                    " FROM filamenti_imp WHERE (\"INSTRUMENT\", \"NAME\") NOT IN (SELECT \"STRUMENTO\", \"FILAMENTO\" \n" +
                                                                                                                " FROM misurazione))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiSegmenti(){
        //OK riusabile. Riempie la tabella "segmenti".

        String fillQuery =  "INSERT INTO segmenti ( SELECT si.\"IDBRANCH\", si.\"TYPE\", fi.\"NAME\"" +
                                                    "FROM scheletri_imp si JOIN filamenti_imp fi ON si.\"IDFIL\" = fi.\"IDFIL\"" +
                                                    "WHERE si.\"N\" = 1 AND (si.\"IDBRANCH\" NOT IN ( SELECT \"IDBRANCH\"" +
                                                                                                        "FROM segmenti)))";
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
        //OK riusabile. Riempie la relazione 'punti_contorni'.

        String fillQuery =  "INSERT INTO punti_contorni (SELECT \"GLON_CONT\", \"GLAT_CONT\" \n" +
                                                       " FROM contorni_imp \n" +
                                                       " WHERE (\"GLON_CONT\", \"GLAT_CONT\") NOT IN (SELECT \"GLON_CONT\", \"GLAT_CONT\"\n" +
                                                                                                    " FROM punti_contorni))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiPuntiSegmenti() {
        //MOLTO LENTA ma riusabile. Riempie la relazione 'punti_segmenti'.

        String fillQuery = "INSERT INTO punti_segmenti(\"SEGMENTO\", \"GLON_BR\", \"GLAT_BR\", \"N\", \"FLUX\", \"NAME_FIL\" ) \n" +
                "                          ( SELECT a.\"IDBRANCH\", a.\"GLON_BR\", a.\"GLAT_BR\", a.\"N\", a.\"FLUX\", b.\"NAME\" \n" +
                "                            FROM scheletri_imp a JOIN filamenti_imp b ON a.\"IDFIL\" = b.\"IDFIL\" \n" +
                "                            WHERE b.\"NAME\" NOT IN (SELECT  \"NAME_FIL\" FROM punti_segmenti ))";
        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void riempiContorni(){
        //MOLTO LENTA. Riempie la relazione 'contorni'.

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
