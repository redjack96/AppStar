package test.RequisitoFunzionale_11;

import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.FileDao.calcolaDistSegCon;

public class CalcolaDistanzaSegConTest {

    @Test
    public void calcolaDistanzaTest() throws SQLException{
        System.out.println("\n-----------VERIFICA CALCOLO DISTANZA SEGMENTO DAL CONTORNO----------\n" +
                "Verifichiamo la distanza del segmento 257 del filamento 409 di Herschel. Sappiamo che il vertice \n" +
                        "più vicino al contorno dista 0.01469, mentre il più lontano dista 0.01472.");
        ArrayList<Float> distanze = calcolaDistSegCon(257, 409, "Herschel");
        Assert.assertEquals(0.01469, distanze.get(0), 0.0002);
        Assert.assertEquals(0.01472, distanze.get(1), 0.0002);
        System.err.println("\nDistanza calcolata CORRETTAMENTE.");
    }
}
