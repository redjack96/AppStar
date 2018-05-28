package test.RequisitoFunzionale_5;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.FileDao.calcolaEstensione;

public class CalcolaEstensioneTest {

    @Test
    public void calcolaEstensioneTest() throws SQLException{
        System.out.println("\n----------VERIFICA CALCOLO ESTENSIONE DI UN FILAMENTO PER NOME----------\n" +
                "Calcolo dell'estensione del filamento HiGALFil015.9322-1.0422 ...");
        ArrayList estensione = calcolaEstensione("HiGALFil015.9322-1.0422", 0, "Herschel");
        Assert.assertNotNull(estensione.get(0));
        Assert.assertNotNull(estensione.get(1));
        System.err.println("L'estensione del filamento HiGALFil015.9322-1.0422 è stato calcolato CORRETTAMENTE.");
    }

    @Test
    public void calcolaEstensioneTestFallito() throws SQLException{
        System.out.println("\n----------VERIFICA CALCOLO ESTENSIONE DI UN FILAMENTO INESISTENTE PER NOME----------\n" +
                "Calcolo dell'estensione del filamento inesistente");
        ArrayList estensione = calcolaEstensione("filamentoACaso", 0, "Herschel");
        Assert.assertEquals("NON TROVATO", estensione.get(0));
        Assert.assertEquals("NON TROVATO", estensione.get(1));
        System.err.println("L'estensione del filamento filamentoACaso NON è stato calcolato.");
    }
}
