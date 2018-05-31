package test.RequisitoFunzionale_12;

import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import static persistence.FileDao.calcolaDistStellaSpina;


public class CalcolaDistanzaStellaSpinaTest {

    @Test
    public void calcolaDistanzaTest() throws SQLException{
        System.out.println("\n-----------VERIFICA CALCOLO DISTANZE DELLE STELLA DALLA SPINA DORSALE----------\n" +
                "Verifichiamo che il metodo che permette all'utente di calcolare le distanze delle stelle contenute \n" +
                "nel filamento 409 di Herschel rispetto alla spina dorsale non restituisca un result set vuoto.");
        boolean x = calcolaDistStellaSpina(null,null,null,null,null,null,null,null,null,409, "Herschel", "distanza", 1);
        Assert.assertEquals(true, x);
        System.err.println("\nDistanze calcolate.");
    }

    @Test
    public void calcolaDistanzaTestFallito() throws SQLException{
        System.out.println("\n-----------VERIFICA CALCOLO DISTANZE DELLE STELLA DALLA SPINA DORSALE----------\n" +
                "Verifichiamo che il metodo che permette all'utente di calcolare le distanze delle stelle contenute \n" +
                "nel filamento 0 di Herschel rispetto alla spina dorsale RESTITUISCA un result set vuoto.");
        boolean x = calcolaDistStellaSpina(null,null,null,null,null,null,null,null,null,0, "Herschel", "flusso", 1);
        Assert.assertEquals(false, x);
        System.err.println("\nDistanze NON calcolate.");
    }
}
