package test.RequisitoFunzionale_7;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;

import java.sql.SQLException;

public class RicercaPerSegTest {

    @Test
    public void ricercaPerSegTest() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare a patto che l'intervallo che identifica il numero \n" +
                "di segmenti che il filamento deve avere non sia inferiore a 3: di conseguenza, se vogliamo \n " +
                "sapere il numero di filamenti trovati aventi dai 20 ai 22 segmenti otterremo 0, perché questo \n" +
                "tipo di ricerca non è consentita. Tuttavia esistono molti filamenti che hanno 21 segmenti, \n" +
                "tra cui il 409 di Herschel.");
        int numRic = new TestDao().req_fun_7_getNoFilamenti(20, 22);
        Assert.assertEquals(0, numRic);
        System.err.println("\nI filamenti NON SONO STATI TROVATI.");
    }
}
