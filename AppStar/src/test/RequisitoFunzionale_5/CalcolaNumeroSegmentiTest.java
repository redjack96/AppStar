package test.RequisitoFunzionale_5;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.FileDao.calcolaNumSeg;

public class CalcolaNumeroSegmentiTest {

    @Test
    public void calcolaNumSegTest() throws SQLException{
        System.out.println("\n----------VERIFICA DEL CALCOLO DEI SEGMENTI DI UN FILAMENTO PER ID----------\n" +
                "Calcolo del numero di segmenti del filamento 409 ...");
        int numSeg = calcolaNumSeg("", 409, "Herschel");
        Assert.assertEquals(21, numSeg);
        System.err.println("Il numero di segmenti del filamento 409 di Herschel sono stati trovati CORRETTAMENTE.");
    }

    @Test
    public void calcolaNumSegTestFallito() throws SQLException{
        System.out.println("\n----------VERIFICA DEL CALCOLO DEI SEGMENTI DI UN FILAMENTO INESISTENTE PER ID----------\n" +
                "Calcolo del numero di segmenti del filamento 0 ...");
        int numSeg = calcolaNumSeg("", 0, "Herschel");
        Assert.assertEquals(-1, numSeg);
        System.err.println("Il numero di segmenti del filamento 0 di Herschel NON e' stato trovato.");
    }
}
