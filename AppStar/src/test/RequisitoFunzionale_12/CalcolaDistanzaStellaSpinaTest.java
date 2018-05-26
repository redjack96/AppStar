package test.RequisitoFunzionale_12;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class CalcolaDistanzaStellaSpinaTest {

    @Test
    public void calcolaDistanzaTest() throws SQLException{
        System.out.println("\n-----------VERIFICA CALCOLO DISTANZE DELLE STELLA DALLA SPINA DORSALE----------\n" +
                "Verifichiamo che il metodo che permette all'utente di calcolare le distanze delle stelle contenute \n" +
                "nel filamento 409 di Herschel rispetto alla spina dorsale non restituisca un result set vuoto.");
        boolean x = new TestDao().req_fun_12_getSuccess(409, "Herschel");
        Assert.assertEquals(true, x);
        System.err.println("\nDistanze calcolate.");
    }
}
