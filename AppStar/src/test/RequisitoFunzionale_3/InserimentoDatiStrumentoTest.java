package test.RequisitoFunzionale_3;

import entity.UtenteAmministratore;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import static entity.UtenteConnesso.utente;

public class InserimentoDatiStrumentoTest {

    @Test
    public void inserimentoDatiStrumento(){
        System.out.println("\n-----------VERIFICA INSERIMENTO DATI STRUMENTO DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di inserire nuovi dati di uno strumento");
        Assert.assertEquals("albertone", utente.getUserID());
        Assert.assertEquals(true, utente.isAmministratore());
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        // x = true se inserimento ha successo
        boolean x = admin.inserisciNuoviDatiStrumento(Float.parseFloat("9.99"), "ANDREA", "Herschel");
        Assert.assertEquals(true, x);
        System.err.println("Alberto HA INSERITO nuovi dati dello strumento 'ANDREA'");
        System.err.println("\n--TEST INSERIMENTO STRUMENTO TERMINATO CON SUCCESSO--\n");
    }

    @Test
    public void inserimentoDatiSatellite(){
        System.out.println("\n-----------VERIFICA INSERIMENTO DATI SATELLITE DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di inserire nuovi dati di un satellite");
        Assert.assertEquals("albertone", utente.getUserID());
        Assert.assertEquals(true, utente.isAmministratore());
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        // x = true se inserimento ha successo
        boolean x = admin.inserisciNuoviDatiSatellite("ASI", "AGILE", LocalDate.of(2007,4,27) , null);
        Assert.assertEquals(true, x);
        System.err.println("Alberto HA INSERITO nuovi dati del satellite 'AGILE'");
        System.err.println("\n--TEST INSERIMENTO SATELLITE TERMINATO CON SUCCESSO, CANCELLAZIONE DATI IN CORSO...--\n");
    }

    @Test
    public void inserimentoDatiStrumentoFallito(){
        System.out.println("\n-----------VERIFICA INSERIMENTO DATI STRUMENTO DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di inserire nuovi dati di uno strumento di un satellite non ancora inserito");
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        // x = true se inserimento ha successo
        boolean x = admin.inserisciNuoviDatiStrumento(Float.parseFloat("3.0"), "ANDREA", "NuovoSatellite");
        Assert.assertEquals(false, x);
        System.err.println("Alberto NON HA INSERITO nuovi dati ");
        System.err.println("\n--TEST INSERIMENTO STRUMENTO senza satellite TERMINATO--\n");
    }

}
