package test.RequisitoFunzionale_3;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import persistence.TestDao;

import java.sql.SQLException;

public class BeforeAfterInserisciDatiStrumentoTest extends InserimentoDatiStrumentoTest{
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
    @AfterClass // esegue dopo tutti i test
    public static void tearDownClass(){
        // elimina strumenti e satelliti inseriti
        try {
            TestDao testDao = new TestDao();
            testDao.req_fun_3_deleteDataStrumento();
            testDao.req_fun_3_deleteDataSatellite();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("\n--CANCELLAZIONE DATI COMPLETATA!--\n");
    }
}
