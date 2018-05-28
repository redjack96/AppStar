package test.RequisitoFunzionale_9;

import org.junit.Assert;
import org.junit.Test;
import static persistence.FileDao.cercaInFilamento;

import java.sql.SQLException;
import java.util.ArrayList;

public class RicercaInFilamentoTest {

    @Test
    public void ricercaStelleInFilamentoTest() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di un filamento specificato \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultimo. Sappiamo che il filamento \n" +
                "409 di Herscel contiene al suo interno 5349 stelle. Verifichiamo tale quantita con quella \n" +
                "proposta dall'applicazione.");
        ArrayList arrayTipi = cercaInFilamento(null, null, null, null, null,
                null, null, null, 409, "Herschel", 1);
        int a = (int) arrayTipi.get(0);
        int b = (int) arrayTipi.get(1);
        int c = (int) arrayTipi.get(2);
        int tot = a+b+c;
        Assert.assertEquals(5349, tot);
        System.err.println("\nTrovata la quantità ESATTA di stelle contenute nel filamento: "+ tot);
    }

    @Test
    public void ricercaStelleInFilamentoTest2() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di un filamento specificato \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultimo. Sappiamo che il filamento \n" +
                "45 di Herschel contiene al suo interno un certo numero di stelle. Verifichiamo che tale quantita non" +
                "sia nulla.");
        ArrayList arrayTipi = cercaInFilamento(null, null, null, null, null,
                null, null, null, 45, "Herschel", 1);
        int a = (int) arrayTipi.get(0); int b = (int) arrayTipi.get(1); int c = (int) arrayTipi.get(2);
        int tot = a+b+c;
        Assert.assertNotEquals(0, tot);
        System.err.println("\nTrovata la quantità di stelle contenute nel filamento: "+ tot);
    }

    @Test
    public void ricercaStelleInFilamentoTest3() throws SQLException{
        System.out.println("\n-----------VERIFICA RICERCA FILAMENTO PER NUMERO DI SEGMENTI----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di un filamento specificato \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultimo. Sappiamo che il filamento \n" +
                "45 di Herschel contiene al suo interno un certo numero di stelle. Verifichiamo che tale quantita non" +
                "sia nulla.");
        ArrayList arrayTipi = cercaInFilamento(null, null, null, null, null,
                null, null, null, 0, "Herschel", 1);
        int a = (int) arrayTipi.get(0); int b = (int) arrayTipi.get(1); int c = (int) arrayTipi.get(2);
        int tot = a+b+c;
        Assert.assertEquals(0, tot);
        System.err.println("\nTrovata nessuna stella nel filamento 0");
    }
}
