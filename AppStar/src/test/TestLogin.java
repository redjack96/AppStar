package test;

import org.junit.Assert;
import org.junit.Test;


import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.UtenteDao.controlloAccount;

public class TestLogin {
    private ArrayList<String> infoLorenzo = new ArrayList<>(6);
    private ArrayList<String> infoGiacomo = new ArrayList<>(6);

    private void definisciInfoLorenzo(){
        infoLorenzo.add("Lorenzo"); infoLorenzo.add("Zara"); infoLorenzo.add("lorzar"); infoLorenzo.add("lorzar");
        infoLorenzo.add("lorenzo.zara96@gmail.com"); infoLorenzo.add("amministratore");
        }
    private void definisciInfoGiacomo(){
        infoGiacomo.add("Giacomo"); infoGiacomo.add("Rossi"); infoGiacomo.add("red_jack"); infoGiacomo.add("redjack");
        infoGiacomo.add("giacomo.redjack@gmail.com"); infoGiacomo.add("notAmministratore");
    }

    @Test
    public void adminVerificato() throws SQLException{
        System.out.println("----------VERIFICA DI LOGIN DA PARTE DI UN AMMINISTRATORE REGISTRATO----------");
        ArrayList infoUtente;
        infoUtente = controlloAccount("lorzar", "lorzar");
        definisciInfoLorenzo();
        Assert.assertEquals(infoLorenzo, infoUtente);
    }

    @Test
    public void adminNonVerificato() throws SQLException{
        System.out.println("----------VERIFICA DI LOGIN DA PARTE DI UN AMMINISTRATORE NON REGISTRATO----------");
        ArrayList infoUtente;
        infoUtente = controlloAccount("noName", "noPass");
        definisciInfoLorenzo();
        Assert.assertNotEquals(infoLorenzo, infoUtente);
    }

    @Test
    public void utenteVerificato() throws SQLException{
        System.out.println("----------VERIFICA DI LOGIN DA PARTE DI UN UTENTE REGISTRATO----------");
        ArrayList infoUtente;
        infoUtente = controlloAccount("red_jack", "redjack");
        definisciInfoGiacomo();
        Assert.assertEquals(infoGiacomo, infoUtente);
    }
}
