package persistence;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;


import static persistence.Connessione.CONN;

public class FileImportazioneDao {

    // Chiamato da entity.UtenteAmministratore per importare uno dei file csv
    public static void importaFile(File file, String relazione, String satellite) throws SQLException {
        long start, elapsed;
        float result;

        Connessione.connettiti();
        String importazione = "COPY " + relazione +" FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";
        //Il path del file da importare non deve contenere \Desktop\.
        //PostgreSQL legge i file in formato csv solamente se provenienti dal disco locale C.

        try {
            //Tronca la tabella imp temporanea scelta per sostituirla con quella nuova.
            PreparedStatement ps1 = CONN.prepareStatement("TRUNCATE " + relazione);
            ps1.executeUpdate();
            System.out.println("Troncata tabella " + relazione);

            switch (relazione) {

                case "contorni_imp": {

                    importazione = "COPY contorni_imp(\"IDFIL\", \"GLON_CONT\", \"GLAT_CONT\") FROM '" + file.getPath() +
                            "' DELIMITER ',' csv HEADER";

                    PreparedStatement ps2 = CONN.prepareStatement("ALTER SEQUENCE \"contorni_imp_N_seq\" " +
                            "RESTART WITH 1");
                    // misuro il tempo
                    start = System.nanoTime();
                    ps2.executeUpdate();

                    PreparedStatement ps3 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");
                    ps3.execute();

                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella punti_contorni...");
                    riempiPuntiContorni(satellite);
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella contorni...");
                    riempiContorni(satellite); // se filamenti_imp e' vuota non la riempie
                    elapsed = System.nanoTime();
                    result = (elapsed - start)/1000000000;
                    System.out.println("COMPLETATO.\nFINE. TEMPO IMPIEGATO CONTORNI: "+ result);
                    break;
                }
                case "filamenti_imp": {
                    PreparedStatement ps4 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");

                    // misuro il tempo
                    start = System.nanoTime();

                    ps4.execute();

                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella filamenti...");
                    riempiFilamenti(satellite); // campo NUMSEG da aggiornare
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella segmenti...");
                    riempiSegmenti(); // vuota se non ho importato scheletri
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella punti_segmenti...");
                    riempiPuntiSegmenti(); // vuota se segmenti e' vuota
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella misurazione...");
                    riempiMisurazione();
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella contorni...");
                    riempiContorni(satellite); //se punti_contorni e' vuota non la riempie
                    elapsed = System.nanoTime();
                    result = (elapsed - start)/1000000000;
                    System.out.println("COMPLETATO.\nFINE. TEMPO IMPIEGATO FILAMENTI: "+ result);

                    break;
                }
                case "scheletri_imp":  //TODO: fare test
                    PreparedStatement ps5 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");

                    // misuro il tempo
                    start = System.nanoTime();

                    ps5.execute();
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella segmenti...");
                    riempiSegmenti();
                    System.out.print("COMPLETATO.\nAggiornamento della tabella filamenti...");
                    aggiornaFilamenti(satellite);
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella punti_segmenti...");
                    riempiPuntiSegmenti();
                    elapsed = System.nanoTime();
                    result = (elapsed - start)/1000000000;
                    System.out.println("COMPLETATO.\nFINE. TEMPO IMPIEGATO SCHELETRI: "+ result);
                    break;
                case "stelle_imp": //OK
                    PreparedStatement ps6 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");

                    // misuro il tempo
                    start = System.nanoTime();

                    ps6.execute();
                    System.out.print(("COMPLETATO.\nImportazione del file csv nella tabella stelle..."));
                    riempiStelle();
                    System.out.print(("COMPLETATO.\nImportazione del file csv nella tabella visibilita'..."));
                    riempiVisibilita(satellite);
                    elapsed = System.nanoTime();
                    result = (elapsed - start)/1000000000;
                    System.out.println("COMPLETATO.\nFINE. TEMPO IMPIEGATO STELLE: "+ result);

                    break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            CONN.close();

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

            System.out.println("Dati aggiunti alle tabelle satelliti e agenzie con successo!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }

    public static void inserisciDatiStrumento(float banda, String strumento, String satellite) throws SQLException{

        Connessione.connettiti();
        String insertStrumenti = " INSERT INTO strumenti(\"STRUMENTO\",\"SAT\") VALUES ('" + strumento + "', '" + satellite + "')" +
                                " ON CONFLICT DO NOTHING";
        String insertBanda =    "INSERT INTO bande(\"WAVE_LENGTH\", \"STRUMENTO\") VALUES " +
                                            "('" + banda + "', '" + strumento + "')";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(insertStrumenti);
            ps1.executeUpdate();
            PreparedStatement ps2 = CONN.prepareStatement(insertBanda);
            ps2.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }

    public static void troncaImp() throws SQLException{
    // Ogni volta che si accede alla schermata di importazione, tronca tutte le tabelle temporanee.
        Connessione.connettiti();
        String troncaContorniImp = "TRUNCATE contorni_imp";
        String troncaFilamentiImp = "TRUNCATE filamenti_imp";
        String troncaScheletriImp = "TRUNCATE scheletri_imp";
        String troncaStelleImp = "TRUNCATE stelle_imp";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(troncaContorniImp);
            PreparedStatement ps2 = CONN.prepareStatement(troncaFilamentiImp);
            PreparedStatement ps3 = CONN.prepareStatement(troncaScheletriImp);
            PreparedStatement ps4 = CONN.prepareStatement(troncaStelleImp);
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps4.executeUpdate();
            System.out.println("Troncate le imp");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }

    }

    private static void riempiFilamenti(String satellite){
        // OK riusabile. Riempe la tabella "filamenti".

        String fillQuery = "INSERT INTO filamenti(\"IDFIL\",\"NAME\",\"SATELLITE\") (" +
                "SELECT \"IDFIL\", \"NAME\", '" + satellite + "' " +
                "FROM filamenti_imp) ON CONFLICT DO NOTHING";
        try {
            PreparedStatement ps = CONN.prepareStatement(fillQuery);
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void aggiornaFilamenti(String satellite){
        // OK riusabile. Aggiorna la colonna NUM_SEG della tabella "filamenti". ATTENZIONE non funziona se c'e' spitzer e inserisco herschel o viceversa!!!

        String updateQuery1 = "UPDATE filamenti f SET \"NUM_SEG\" = (SELECT COUNT(*) " +
                "FROM scheletri_imp si " +
                "WHERE (f.\"IDFIL\" = si.\"IDFIL\" AND" +
                " \"N\" = 1))" +
                "WHERE \"SATELLITE\" = '"+satellite+"'";
        try {
            PreparedStatement ps = CONN.prepareStatement(updateQuery1);
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiStelle(){
        //OK riusabile. Riempie la tabella "stelle".

        String fillQuery =  "INSERT INTO stelle  (    SELECT * " +
                                                      "FROM stelle_imp " +
                                                      ") ON CONFLICT DO NOTHING";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiVisibilita(String satellite){
        //OK riusabile. Riempie la tabella "visibilita". Il satellite e' scelto nell'applicazione

        String fillQuery =  "INSERT INTO visibilita  (    SELECT '" + satellite + "', \"NAMESTAR\" " +
                                                        "FROM stelle_imp " +
                                                        ") ON CONFLICT DO NOTHING";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiMisurazione(){
        //OK riusabile. Riempie la tabella "misurazione".

        String fillQuery =  "INSERT INTO misurazione (SELECT \"INSTRUMENT\", \"NAME\", \"MEAN_DENS\", \"MEAN_TEMP\", \"ELLIPTICITY\", \"CONTRAST\", \"TOTAL_FLUX\" \n" +
                                                    " FROM filamenti_imp) ON CONFLICT DO NOTHING";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiSegmenti(){
        //OK riusabile. Riempie la tabella "segmenti". Herschel: 4 secondi. Spitzer 8 secondi.

        String fillQuery =  "INSERT INTO segmenti ( SELECT DISTINCT si.\"IDBRANCH\", si.\"TYPE\", fi.\"NAME\" " +
                                                  " FROM scheletri_imp si JOIN filamenti_imp fi ON si.\"IDFIL\" = fi.\"IDFIL\" " +
                                                  " WHERE  si.\"N\"= 1) ON CONFLICT DO NOTHING";
        try {
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiPuntiContorni(String satellite){
        //OK riusabile. Riempie la relazione 'punti_contorni'.

            //[HERSCHEL]18 secondi se vuota, 2 secondi se piena
            //[SPITZER] 1 min se vuota, 9 secondi se piena
            String query = "INSERT INTO punti_contorni (SELECT \"GLON_CONT\", \"GLAT_CONT\",\"N\",'" + satellite + "'" +
                                                      " FROM contorni_imp " +
                                                      " ) ON CONFLICT DO NOTHING;";

            try {

                PreparedStatement ps2 = CONN.prepareStatement(query);
                ps2.executeUpdate();

            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }


    private static void riempiPuntiSegmenti() {
        //MOLTO LENTA ma riusabile. Riempie la relazione 'punti_segmenti'.


        try{
            PreparedStatement ps0 = CONN.prepareStatement("SELECT count(*)" +
                                                              " FROM punti_segmenti");
            ResultSet rs = ps0.executeQuery(); rs.next();

            int restart = rs.getInt(1) + 1;

            // a seconda del numero di righe inserisco gli IDSEG nella tabella punti_segmenti
            PreparedStatement ps = CONN.prepareStatement("ALTER SEQUENCE \"punti_segmenti_IDSEG_seq\" " +
                    "RESTART WITH " + restart);
            ps.executeUpdate();
            String fillQuery = "INSERT INTO punti_segmenti(\"SEGMENTO\", \"GLON_BR\", \"GLAT_BR\", \"N\", \"FLUX\", \"NAME_FIL\" ) \n" +
                    "                          ( SELECT a.\"IDBRANCH\", a.\"GLON_BR\", a.\"GLAT_BR\", a.\"N\", a.\"FLUX\", b.\"NAME\" \n" +
                    "                            FROM scheletri_imp a JOIN filamenti_imp b ON a.\"IDFIL\" = b.\"IDFIL\" \n" +
                    "                            ) ON CONFLICT DO NOTHING";

            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiContorni(String satellite){
        // Riempie la relazione 'contorni'.

        String fillQuery =  "INSERT INTO contorni  (  " +
                "SELECT fi.\"NAME\", ci.\"N\", '" + satellite + "' " +
                "FROM filamenti_imp fi JOIN contorni_imp ci ON fi.\"IDFIL\" = ci.\"IDFIL\" " +
                ") ON CONFLICT DO NOTHING";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

/* la freccia significa "richiede dati da"; fk = foreign key: I dati devono essere presenti nella tabella tra parentesi
* Satelliti   ok          -> NULLA
* Agenzie     ok          -> (fk satelliti)
* Strumenti             -> (fk satelliti)
* Bande       ok          -> (fk strumenti)
* Stelle      ok          -> stelle_imp
* Visibilita  ok          -> stelle_imp (fk satelliti, stelle)
* Filamenti   ok          -> filamenti_imp JOIN scheletri_imp (fk satelliti)
* Segmenti    (ok)          -> filamenti_imp JOIN scheletri_imp (fk filamenti)
* Filamenti (NumSeg)      -> scheletri_imp, segmenti
* PuntiSegmenti (ok)        -> filamenti_imp JOIN scheletri_imp (fk segmenti e a sua volta filamenti)
* Misurazione   ok        -> filamenti_imp (fk Strumenti e fk filamenti)
* PuntiContorni ok        -> contorni_imp (fk satelliti)
* Contorni      ok        -> contorni_imp JOIN filamenti_imp (fk filamenti e fk punti_contorni , che a sua volta richiede fk satelliti)
* */
