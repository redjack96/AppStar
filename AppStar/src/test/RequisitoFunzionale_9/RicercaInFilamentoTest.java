package test.RequisitoFunzionale_9;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;

import java.sql.SQLException;

public class RicercaInFilamentoTest {

    @Test
    public void ricercaInFilamentoTest() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di un filamento specificato \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultimo. Sappiamo che il filamento \n" +
                "409 di Herscel contiene al suo interno 5349 stelle. Verifichiamo tale quantita con quella \n" +
                "proposta dall'applicazione.");
        int numRic = new TestDao().req_fun_9_getNoStelle(409, "Herschel");
        Assert.assertEquals(5349, numRic);
        System.err.println("\nTrovata la quantità ESATTA di stelle contenute nel filamento.");
    }
}
