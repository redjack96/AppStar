package test.RequisitoFunzionale_1_e_2;

import org.junit.Assert;
import org.junit.Test;


import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.UtenteDao.controlloAccount;

public class LoginTest {
    private ArrayList<String> infoLorenzo = new ArrayList<>(6);
    private ArrayList<String> infoGiacomo = new ArrayList<>(6);

    private void setInfoLorenzo(){
        //Sono i valori attesi dopo il tentantivo di login con le seguenti credenziali: {userID: lorzar, pass: lorzar}
        this.infoLorenzo.add("Lorenzo"); this.infoLorenzo.add("Zara"); this.infoLorenzo.add("lorzar"); this.infoLorenzo.add("lorzar");
        this.infoLorenzo.add("lorenzo.zara96@gmail.com"); this.infoLorenzo.add("amministratore");
    }

    private void setInfoGiacomo(){
        //Sono i valore attesi dopo il tentativo di login con le seguenti credenziali: {userID: red_jack, pass:redjack}
        this.infoGiacomo.add("Giacomo"); this.infoGiacomo.add("Rossi"); this.infoGiacomo.add("red_jack"); this.infoGiacomo.add("redjack");
        this.infoGiacomo.add("giacomo.redjack@gmail.com"); this.infoGiacomo.add("notAmministratore");
    }

    @Test
    public void adminVerificato() throws SQLException{
        System.out.println("\n----------VERIFICA DI LOGIN DA PARTE DI UN AMMINISTRATORE REGISTRATO----------\n" +
                "Lorenzo è un amministratore e sta provando ad accedere con le sue credenziali : {userID: lorzar, pass: lorzar}");
        ArrayList infoUtente;
        infoUtente = controlloAccount("lorzar", "lorzar");
        setInfoLorenzo();
        Assert.assertEquals(this.infoLorenzo, infoUtente);
        System.err.println("Lorenzo È riuscito a connettersi.");
    }

    @Test
    public void utenteNonVerificato() throws SQLException{
        System.out.println("\n----------VERIFICA DI LOGIN DA PARTE DI UN UTENTE NON REGISTRATO----------\n" +
                "Tizio non è un utente registrato nel database e sta provando ad accedere con credenziali non identificate.");
        ArrayList infoUtente;
        infoUtente = controlloAccount("noName", "noPass");
        Assert.assertEquals(new ArrayList<String>(), infoUtente); // lista vuota = infoUtente ?
        System.err.print("Tizio NON È riucito a connettersi.");
    }

    @Test
    public void utenteVerificato() throws SQLException{
        System.out.println("----------VERIFICA DI LOGIN DA PARTE DI UN UTENTE REGISTRATO----------\n" +
                "Giacomo è un utente registrato e sta provando ad accedere con le sue credenziali : {userID: red_jack, pass: redjack}");
        ArrayList infoUtente;
        infoUtente = controlloAccount("red_jack", "redjack");
        setInfoGiacomo();
        Assert.assertEquals(this.infoGiacomo, infoUtente);
        System.err.println("Giacomo È riuscito a connettersi.");
    }
}
