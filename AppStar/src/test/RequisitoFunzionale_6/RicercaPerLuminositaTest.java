package test.RequisitoFunzionale_6;

import org.junit.Assert;
import org.junit.Test;
import persistence.TestDao;
import static persistence.FileDao.cercaFilamenti;

import java.sql.SQLException;

public class RicercaPerLuminositaTest {

    @Test
    public void ricercaFil() throws SQLException{
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO PER LUMINOSITÀ E ELLITTICITÀ----------\n" +
                "Cerchiamo i filamenti in un range compreso tra 2.3 e 9.9,  ad avere una luminosità maggiore del 40%. Ci \n" +
                "aspettiamo dunque che dopo una ricerca dei filamenti per luminosità passando i parametri 825 \n" +
                "(percentuale luminosità), 2.3 (range minimo ellitticità) e 9.9 (range massimo ellitticità) l'applicazione \n" +
                "ci comunichi che è stato trovato un numero di ricorrenze diverso da 0.");
        int numRic = cercaFilamenti(null, null, null, null, null, null,
                null, null, 40,(float) 2.3,(float) 9.9, 1).get(0);
        Assert.assertNotEquals(0, numRic);
        System.err.println("\nIl numero di ricorrenze di filamenti dati i parametri in input è stato trovato CORRETTAMENTE: "+ numRic);
    }

    @Test
    public void ricercaConLuminositaNegativa() throws SQLException{
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO CON LUMINOSITÀ NEGATIVA----------\n" +
                "Cerchiamo i filamenti con una luminosità negativa. Questo e' impossibile quindi ci aspettiamo " +
                "che non venga trovato nessun filamento.");

        int numRic = cercaFilamenti(null, null, null, null, null, null,
                null, null, -5,(float) 2.3,(float) 9.9, 1).get(0);
        Assert.assertEquals(0, numRic);
        System.err.println("\nIl numero di ricorrenze di filamenti dati i parametri in input NON è stato trovato.");
    }

    @Test
    public void ricercaConEllitticitaFuoriRange() throws SQLException{
        System.out.println("\n----------VERIFICA RICERCA FILAMENTO CON ELLITTICITÀ FUORI RANGE----------\n" +
                "Cerchiamo i filamenti con un contrasto fuori range. Questo e' impossibile quindi ci aspettiamo " +
                "che non venga trovato nessun filamento.");

        int numRic = cercaFilamenti(null, null, null, null, null, null,
                null, null, 20,(float) 1.0,(float) 11.5, 1).get(0);
        Assert.assertEquals(0, numRic);
        System.err.println("\nIl numero di ricorrenze di filamenti dati i parametri in input NON è stato trovato.");
    }
}
