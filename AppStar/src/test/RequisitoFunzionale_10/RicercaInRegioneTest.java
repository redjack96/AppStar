package test.RequisitoFunzionale_10;

import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import static persistence.FileDao.cercaInRegione;

public class RicercaInRegioneTest {

    @Test
    public void ricercaStelleRegioneTest() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA STELLA IN REGIONE----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di una regione rettangolare \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultima. Sappiamo che la regione con questi dati: " +
                "base 12, altezza 4, centroide (0,0) contiene 702 stelle. Verifichiamo che tale quantita' sia calcolata correttamente");
        ArrayList arrayTipi = cercaInRegione(null, null, null, null, null,
                null, null, null, 4,  12,  0, 0,1);
        int a = (int) arrayTipi.get(0); int b = (int) arrayTipi.get(1); int c = (int) arrayTipi.get(2);
        int d = (int) arrayTipi.get(3); int e = (int) arrayTipi.get(4); int f = (int) arrayTipi.get(5);
        int tot = a+b+c+d+e+f;
        Assert.assertEquals(702, tot);
        System.err.println("\nTrovate con SUCCESSO "+tot+" stelle nel rettangolo con h=4, b=12, centro (0,0)");
    }

    @Test
    public void ricercaStelleRegioneTestFallito() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA STELLA IN REGIONE----------\n" +
                "L'applicazione permette di ricercare tutte le stelle all'interno di una regione rettangolare \n" +
                "ed in particolare di sapere quante stelle contiene quest'ultima. Sappiamo che la regione con questi dati: " +
                "base 4, altezza 12, centroide (0,0) contiene 0 stelle. Verifichiamo che tale quantita' sia calcolata correttamente");
        ArrayList arrayTipi = cercaInRegione(null, null, null, null, null,
                null, null, null, 4, 12, 0, 0, 1);
        int a = (int) arrayTipi.get(0);
        int b = (int) arrayTipi.get(1);
        int c = (int) arrayTipi.get(2);
        int d = (int) arrayTipi.get(3);
        int e = (int) arrayTipi.get(4);
        int f = (int) arrayTipi.get(5);
        int tot = a + b + c + d + e + f;
        Assert.assertEquals(702, tot);
        System.err.println("\nNon e' stata trovata nessuna stella nel rettangolo con h=12, b=4, centro (0,0)");
    }
}
