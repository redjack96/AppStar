package test.RequisitoFunzionale_7;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;
import static persistence.FileDao.cercaFilamentiSeg;

import java.sql.SQLException;

public class RicercaPerSegTest {

    @Test
    public void ricercaPerSegTest() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "Vogliamo conoscere il numero di filamenti trovati aventi dai 5 ai 30 segmenti");
        int numRic = cercaFilamentiSeg(null,null,null,null,null,
                null,5, 30,1 );
        Assert.assertNotEquals(0, numRic);
        System.err.println("\nIl numero di segmenti dei filamenti SONO STATI TROVATI. Filamenti trovati: "+ numRic);
    }
    @Test
    public void ricercaPerSegTestFallito() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare a patto che l'intervallo che identifica il numero \n" +
                "di segmenti che il filamento deve avere non sia inferiore a 3: di conseguenza, se vogliamo \n " +
                "sapere il numero di filamenti trovati aventi dai 20 ai 22 segmenti otterremo 0, perché questo \n" +
                "tipo di ricerca non è consentita. Tuttavia esistono molti filamenti che hanno 21 segmenti, \n" +
                "tra cui il 409 di Herschel.");
        int numRic = cercaFilamentiSeg(null,null,null,null,null,
                null,20, 22,1 );
        Assert.assertEquals(0, numRic);
        System.err.println("\nI filamenti NON SONO STATI TROVATI.");
    }
}
