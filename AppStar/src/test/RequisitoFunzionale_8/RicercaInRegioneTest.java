package test.RequisitoFunzionale_8;

import org.junit.Assert;
import org.junit.Test;
import static persistence.FileDao.cercaFilamentiInRegione;

import java.sql.SQLException;

public class RicercaInRegioneTest {

    @Test
    public void ricercaFilInQuadratoTest() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO IN REGIONE----------\n" +
                "Cerchiamo i filamenti in una regione quadrata di lato 20 e centroide in (0,0)");
        int successo = cercaFilamentiInRegione(null, null, null, null, null,
                null,20,  (float) 0.0,(float) 0.0, true, 1);
        Assert.assertEquals(1,successo);
        System.err.println("\nLa ricerca nel quadrato e' stata effettuata con successo");
    }

    @Test
    public void ricercaFilInCerchioTest() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO IN REGIONE----------\n" +
                "Cerchiamo i filamenti in una regione circolare di raggio 50 e centro in (0,0)");
        int successo = cercaFilamentiInRegione(null, null, null, null, null,
                null,50,  (float) 0.0,(float) 0.0, false, 1);
        Assert.assertEquals(1,successo);
        System.err.println("\nLa ricerca nel cerchio e' stata effettuata con successo");
    }

    @Test
    public void ricercaFilInCerchioTestFallita() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO IN REGIONE----------\n" +
                "Cerchiamo i filamenti in una regione circolare di raggio negativo e centro in (0,0)");
        int insuccesso = cercaFilamentiInRegione(null, null, null, null, null,
                null,-1,  (float) 0.0,(float) 0.0, false, 1);
        Assert.assertEquals(0,insuccesso);
        System.err.println("\nLa ricerca nel cerchio NON e' stata effettuata con successo");
    }
    @Test
    public void ricercaFilInQuadratoTestFallita() throws SQLException {
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO IN REGIONE----------\n" +
                "Cerchiamo i filamenti in una regione quadrata di lato negativo e centroide in (0,0)");
        int insuccesso = cercaFilamentiInRegione(null, null, null, null, null,
                null,-1,  (float) 0.0,(float) 0.0, false, 1);
        Assert.assertEquals(0,insuccesso);
        System.err.println("\nLa ricerca nel quadrato NON e' stata effettuata con successo");
    }

}
