package test.RequisitoFunzionale_3;

import entity.UtenteAmministratore;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static entity.UtenteConnesso.utente;

public class InserimentoDatiStrumentoTest {

    @Test
    public void inserimentoDati(){
        System.out.println("\n-----------VERIFICA INSERIMENTO DATI STRUMENTO DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di inserire nuovi dati di uno strumento");
        Assert.assertEquals("albertone", utente.getUserID());
        Assert.assertEquals(true, utente.isAmministratore());
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        boolean x = admin.inserisciNuoviDatiStrumento(Float.parseFloat("9.99"), "ANDREA", "Herschel");
        Assert.assertEquals(true, x);
        System.err.println("Alberto HA INSERITO nuovi dati dello strumento 'ANDREA'");
    }
}
