package persistence;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.math.BigDecimal;

import static persistence.Connessione.CONN;

public class FileDao {

    // Chiamato da entity.UtenteAmministratore per importare uno dei file csv
    public static void importaFile(File file, String relazione, String satellite) throws SQLException {

        Connessione.connettiti();
        String importazione = "COPY " + relazione +" FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";
        //Il path del file da importare non deve contenere \Desktop\.
        //PostgreSQL legge i file in formato csv solamente se provenienti dal disco locale C.

        try {
            //Tronca la tabella imp temporanea scelta per sostituirla con quella nuova.
            //TODO: quando si passa alla schermata importaFileSatelliteGUI TRONCARE tutte le _imp. (NON quando si preme IMPORTA)
            PreparedStatement ps1 = CONN.prepareStatement("TRUNCATE " + relazione);
            ps1.executeUpdate();
            System.out.println("Troncata tabella " + relazione);

            // PRIMA: if (relazione.equals("contorni_imp")) ecc.
            switch (relazione) {
                // FUNZIONA per Herschel non impiega tanto
                case "contorni_imp": {

                    importazione = "COPY contorni_imp(\"IDFIL\", \"GLON_CONT\", \"GLAT_CONT\") FROM '" + file.getPath() +
                            "' DELIMITER ',' csv HEADER";

                    /*PreparedStatement ps2 = CONN.prepareStatement("SELECT COUNT(*) as id FROM punti_contorni");
                    ResultSet rs = ps2.executeQuery();
                    while (rs.next()){
                        ID = rs.getInt("id");
                    }*/
                    PreparedStatement ps2 = CONN.prepareStatement("ALTER SEQUENCE \"contorni_imp_N_seq\" " +
                            "RESTART WITH 1");
                    ps2.executeUpdate();

                    PreparedStatement ps3 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");
                    ps3.execute();
                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella punti_contorni...");

                    riempiPuntiContorni(satellite, file);
                    System.out.print("COMPLETATO.\n");

                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella contorni...\n");
                    riempiContorni(satellite); // se filamenti_imp e' vuota non la riempie
                    System.out.println("COMPLETATO.\nFINE");
                    break;
                }
                case "filamenti_imp": {
                    PreparedStatement ps4 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");
                    ps4.execute();
                    /*
                    riempiFilamenti()
                    riempiSegmenti()
                    riempiFilamenti()
                    riempiPuntiSegmenti()
                    riempiMisurazione()
                    */

                    System.out.print("COMPLETATO.\nImportazione del file csv nella tabella contorni...\n");
                    riempiContorni(satellite); //se punti_contorni e' vuota non la riempie
                    System.out.println("COMPLETATO.\nFINE");

                    break;
                }
                case "scheletri_imp":
                    PreparedStatement ps5 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");
                    ps5.execute();
                    System.out.println(("COMPLETATO.\nNot implemented"));
                    break;
                case "stelle_imp":
                    PreparedStatement ps6 = CONN.prepareStatement(importazione);
                    System.out.print("Importazione del file csv nella tabella " + relazione + "...");
                    ps6.execute();
                    System.out.print(("COMPLETATO.\nImportazione del file csv nella tabella stelle..."));
                    riempiStelle();
                    System.out.print(("COMPLETATO.\nImportazione del file csv nella tabella visibilita'..."));
                    riempiVisibilita(satellite);
                    System.out.println("COMPLETATO.\nFINE");

                    break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            CONN.close();
            System.exit(1); //TODO eliminare la riga, quando tutti gli errori delle query sono risolti
        } catch (IOException e) {
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

            System.out.println("Dati aggiunti alle tabelle satelliti e agenzie con successo!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
    }
    //TODO: Prima inserire il nome dello strumento in 'strumenti' poi le bande in 'bande'.
    public static void inserisciDatiStrumento(float banda, String strumento) throws SQLException{

        Connessione.connettiti();
        String insertStrumenti = " INSERT INTO strumenti";
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

    public static void troncaImp() throws SQLException{

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

    private static void riempiFilamenti() throws SQLException{
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
        } finally {
            CONN.close();
        }
    }

    private static void riempiStelle(){
        //OK riusabile. Riempie la tabella "stelle".

        String fillQuery =  "INSERT INTO stelle  (    SELECT *" +
                                                      "FROM stelle_imp " +
                                                      "WHERE \"NAMESTAR\" NOT IN (  SELECT \"NAME_STAR\"" +
                                                                                  "FROM stelle))";

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
                                                        "WHERE \"NAMESTAR\" NOT IN (SELECT \"STELLA\"" +
                                                        "FROM visibilita))";

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
                                                    " FROM filamenti_imp WHERE (\"INSTRUMENT\", \"NAME\") NOT IN (SELECT \"STRUMENTO\", \"FILAMENTO\" \n" +
                                                                                                                " FROM misurazione))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void riempiSegmenti(){
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

    private static void riempiPuntiContorni(String satellite, File file) throws IOException{
        //OK riusabile. Riempie la relazione 'punti_contorni'.

        /*BufferedReader br;
        String line;
        String csvSplitter = ",";
        br = new BufferedReader(new FileReader(file.getPath()));
        int i = 0;
        br.readLine(); // HEADER
        while((line = br.readLine()) != null) {
            //Array di Stringhe di elementi di una tupla.
            String[] tupla = line.split(csvSplitter);
            //Inserisce gli elementi nel database. Cosa ho cambiato: tolto "GLON_CONT", "GLAT_CONT", dopo CONFLICT. Specificato punti_contorni su N e SATELLITE nel WHERE
            i++;
            //[Herschel] = 400 secondi o piu'
            String query = "INSERT INTO punti_contorni (\"GLON_CONT\", \"GLAT_CONT\", \"N\", \"SATELLITE\") VALUES (" + tupla[1] + ", " + tupla[2] + ", " + i + ", '" + satellite + "') " +
                    " ON CONFLICT (\"N\", \"SATELLITE\")" +
                    " DO UPDATE SET (glon_cont, glat_cont) = (EXCLUDED.glon_cont, EXCLUDED.glat_cont)";
            String query = "INSERT INTO punti_contorni (\"GLON_CONT\", \"GLAT_CONT\", \"N\", \"SATELLITE\") VALUES (?, ?, ?, ?) "+
                    " ON CONFLICT (\"N\", \"SATELLITE\")" +
                    " DO UPDATE SET (\"GLON_CONT\", \"GLAT_CONT\") = (EXCLUDED.\"GLON_CONT\", EXCLUDED.\"GLAT_CONT\")";*/


            //[HERSCHEL]18 secondi se vuota, 2 secondi se piena
            //[SPITZER] 1 min se vuota, 9 secondi se piena
            String query = "INSERT INTO punti_contorni (SELECT \"GLON_CONT\", \"GLAT_CONT\",\"N\",'" + satellite + "'" +
                                                      " FROM contorni_imp " +
                                                      " WHERE NOT EXISTS(" +
                    " SELECT \"N\"" +
                    " FROM punti_contorni\n " +
                    " WHERE punti_contorni.\"SATELLITE\" = '" + satellite + "' AND punti_contorni.\"N\" = contorni_imp.\"N\"));";
            /*// Per aggiornare...
            String queryUpdate = "UPDATE punti_contorni a " +
                    " SET (\"GLON_CONT\", \"GLAT_CONT\") = (SELECT \"GLON_CONT\", \"GLAT_CONT\" " +
                    " FROM contorni_imp c " +
                    " WHERE c.\"N\" = a.\"N\") " +
                    " WHERE \"SATELLITE\" = '" + satellite +"';";*/
            try {
                //PreparedStatement ps1 = CONN.prepareStatement(query);
                /*ps1.setBigDecimal(1, new BigDecimal(tupla[1]));
                ps1.setBigDecimal(2, new BigDecimal(tupla[2]));
                ps1.setInt(3, i);
                ps1.setString(4,satellite);*/

                //ps1.executeUpdate();

                PreparedStatement ps2 = CONN.prepareStatement(query);
                ps2.executeUpdate();

            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }


    private static void riempiPuntiSegmenti() {
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

    private static void riempiContorni(String satellite){
        //MOLTO LENTA. Riempie la relazione 'contorni'.

        String fillQuery =  "INSERT INTO contorni  (  " +
                "SELECT fi.\"NAME\", ci.\"N\", '" + satellite + "' " +
                "FROM filamenti_imp fi JOIN contorni_imp ci ON fi.\"IDFIL\" = ci.\"IDFIL\" " +
                "WHERE NOT EXISTS ( " +
                    "SELECT contorni.* " +
                    "FROM contorni " +
                    "WHERE (\"NAME_FIL\", \"NPCONT\", \"SATELLITE\") = (fi.\"NAME\", ci.\"N\", '"+ satellite +"')))";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(fillQuery);
            ps1.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

/* la freccia significa "richiede dati da"; fk = foreign key: I dati devono essere presenti nella tabella tra parentesi
* Satelliti             -> NULLA
* Agenzie               -> (fk satelliti)
* Strumenti             -> (fk satelliti)
* Bande                 -> (fk strumenti)
* Stelle                -> stelle_imp
* Visibilita            -> stelle_imp (fk satelliti, stelle)
* Filamenti             -> filamenti_imp JOIN scheletri_imp
* Segmenti              -> filamenti_imp JOIN scheletri_imp (fk filamenti)
* Filamenti (NumSeg)    -> scheletri_imp, segmenti
* PuntiSegmenti         -> filamenti_imp JOIN scheletri_imp (fk segmenti e a sua volta filamenti)
* Misurazione           -> filamenti_imp (fk Strumenti e fk filamenti)
* PuntiContorni         -> contorni_imp (fk satelliti)
* Contorni              -> contorni_imp JOIN filamenti_imp (fk filamenti e fk punti_contorni , che a sua volta richiede fk satelliti)
* */
