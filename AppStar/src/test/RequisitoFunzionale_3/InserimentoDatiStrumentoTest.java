package test.RequisitoFunzionale_3;

import entity.UtenteAmministratore;
import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;

import java.sql.SQLException;

import static entity.UtenteConnesso.utente;

public class InserimentoDatiStrumentoTest {

    @Test
    public void inserimentoDati() throws SQLException{
        System.out.println("\n-----------VERIFICA INSERIMENTO DATI STRUMENTO DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di inserire nuovi dati di uno strumento");
        Assert.assertEquals("albertone", utente.getUserID());
        Assert.assertEquals(true, utente.isAmministratore());
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        boolean x = admin.inserisciNuoviDatiStrumento(Float.parseFloat("9.99"), "ANDREA", "Herschel");
        Assert.assertEquals(true, x);
        System.err.println("Alberto HA INSERITO nuovi dati dello strumento 'ANDREA'");
        System.err.println("\n--ULTIMO TEST DEL PACCHETTO Suite TERMINATO CON SUCCESSO, CANCELLAZIONE DATI IN CORSO...--\n");
        new TestDao().req_fun_3_deleteData();
        System.err.println("\n--CANCELLAZIONE DATI COMPLETATA!--\n");
    }
}
