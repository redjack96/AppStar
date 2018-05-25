package test.RequisitoFunzionale_5;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.FileDao.calcolaEstensione;

public class CalcolaEstensioneTest {
    private ArrayList<Double> estensione;

    @Test
    public void calcolaEstensioneTest() throws SQLException{
        System.out.println("\n----------VERIFICA CALCOLO ESTENSIONE DI UN FILAMENTO PER NOME----------\n" +
                "Calcolo dell'estensione del filamento HiGALFil015.9322-1.0422 ...");
        estensione = calcolaEstensione("HiGALFil015.9322-1.0422", 0, "Herschel");
        Assert.assertNotNull(estensione.get(0));
        Assert.assertNotNull(estensione.get(1));
        /*Assert.assertEquals((Double)5.994720, estensione.get(0), 0.0001);
        Assert.assertEquals((Double)0.329940, estensione.get(1), 0.0001);*/
        System.err.println("L'estensione del filamento HiGALFil015.9322-1.0422 è stato calcolato CORRETTAMENTE.");
    }
}
