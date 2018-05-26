package test.RequisitoFunzionale_6;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;

import java.sql.SQLException;

public class RicercaPerLuminositaTest {

    @Test
    public void ricercaFil() throws SQLException{
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO PER LUMINOSITÀ----------\n" +
                "In un range compreso tra 2 e 9, sappiamo che l'unico filamento ad avere una luminosità di poco \n" +
                "maggiore del 825% rispetto al bordo è il numero 8696 di Spitzer, ossia SDC333.291-0.421 . Ci \n" +
                "aspettiamo dunque che dopo una ricerca dei filamenti per luminosità passando i parametri 825 \n" +
                "(percentuale luminosità), 2 (range minimo ellitticità) e 9 (range massimo ellitticità) l'applicazione \n" +
                "ci comunichi che è stata trovata una sola ricorrenza.");
        int numRic = new TestDao().req_fun_6_getNoFilamenti(825, 2, 9);
        Assert.assertEquals(1, numRic);
        System.err.println("\nIl numero di ricorrenze di filamenti dati i parametri in input è stato trovato CORRETTAMENTE");
    }
}
