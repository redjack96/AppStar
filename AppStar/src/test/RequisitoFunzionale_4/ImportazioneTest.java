package test.RequisitoFunzionale_4;

import entity.UtenteAmministratore;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static entity.UtenteConnesso.utente;

public class ImportazioneTest {

    @Test
    public void importazioneTest1(){
        System.out.println("\n-----------VERIFICA IMPORTAZIONE DA PARTE DEL NUOVO ADMIN REGISTRATO----------\n" +
                "Alberto, il nuovo amministratore, tenta di importare nuovi file");
        Assert.assertEquals("albertone", utente.getUserID());
        Assert.assertEquals(true, utente.isAmministratore());
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        File file = new File("filamenti_Herschel.csv");
        // x=1 significa che l'importazione avviene con successo
        int x = admin.importaFileCSV(file, 2, "Herschel");
        Assert.assertEquals(1, x);
        System.err.println("Alberto HA INSERITO o SOVRASCRITTO nuovi dati dei filamenti");
        System.err.println("\n--TEST 1 IMPORTAZIONE FILE  TERMINATO CON SUCCESSO--\n");
    }
    @Test
    public void importazioneTest2(){
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        File file = new File("stelle_Herschel.csv");
        // x=1 significa che l'importazione avviene con successo
        int x = admin.importaFileCSV(file, 4, "Herschel");
        Assert.assertEquals(1, x);
        System.err.println("Alberto HA INSERITO o SOVRASCRITTO nuovi dati delle stelle");
        System.err.println("\n--TEST 2 IMPORTAZIONE FILE  TERMINATO CON SUCCESSO--\n");
    }

    @Test
    public void importazioneTest3(){
        UtenteAmministratore admin = new UtenteAmministratore(utente.getNome(), utente.getCognome(), utente.getUserID(),
                utente.getPassword(), utente.getEmail());
        File file = new File("Herschel_nulla.txt");
        // x=1 significa che l'importazione avviene con successo
        int x = admin.importaFileCSV(file, 4, "Herschel");
        Assert.assertNotEquals(1, x);
        System.err.println("Alberto NON è riuscito a inserire il file " + file + " in quanto non e' un file csv");
        System.err.println("\n--TEST 3 IMPORTAZIONE FILE  TERMINATO CON SUCCESSO--\n");
    }
}
