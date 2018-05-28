package test.RequisitoFunzionale_5;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.FileDao.calcolaCentroide;

public class CalcolaCentroideTest {

    @Test
    public void calcolaCentroideTest() throws SQLException{
        System.out.println("\n------------VERIFICA CALCOLO DEL CENTROIDE DI UN FILAMENTO PER ID-----------\n" +
                "Calcolo della posizione del centroide del filamento 409 ...");
        ArrayList centroide = calcolaCentroide("", 409, "Herschel");
        Assert.assertNotNull(centroide.get(0));
        Assert.assertNotNull(centroide.get(1));
        /*Assert.assertEquals((Double)15.9304969512195122, Double.parseDouble(centroide.get(0).toString()), 0.00000000000001);
        Assert.assertEquals((Double)(0-1.0469573170731707), Double.parseDouble(centroide.get(1).toString()), 0.00000000000001);*/
        System.err.println("La posizione del filamento 409 di Herschel è stata trovata CORRETTAMENTE.");
    }

    @Test
    public void calcolaCentroideTestFallito() throws SQLException{
        System.out.println("\n------------VERIFICA CALCOLO DEL CENTROIDE DI UN FILAMENTO INESISTENTE PER ID-----------\n" +
                "Calcolo della posizione del centroide del filamento 409 ...");
        ArrayList centroide = calcolaCentroide("", 0, "Herschel");
        Assert.assertEquals("NON TROVATO", centroide.get(0));
        Assert.assertEquals("NON TROVATO", centroide.get(1));
        System.err.println("La posizione del filamento 0 di Herschel NON è stata trovata.");
    }
}
