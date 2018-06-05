package test.RequisitoFunzionale_4;

import org.junit.BeforeClass;
import persistence.TestDao;

import java.sql.SQLException;

public class BeforeImportazioneTest extends ImportazioneTest{
    @BeforeClass // esegue prima di tutti i test
    public static void setUpClass(){
        // elimina l'utente albertone
        try {
            new TestDao().deleteAlberto();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("\n--CONFIGURAZIONE DATABASE COMPLETATA!--\n");
    }
}
