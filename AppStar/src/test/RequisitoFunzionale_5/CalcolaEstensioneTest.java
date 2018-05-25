package test.RequisitoFunzionale_5;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static persistence.FileDao.calcolaEstensione;

public class CalcolaEstensioneTest {
    private float estensione;

    @Test
    public void calcolaEstensioneTest() throws SQLException{
        System.out.println("\n----------VERIFICA CALCOLO ESTENSIONE DI UN FILAMENTO PER NOME----------\n");
        calcolaEstensione("HiGALFil015.9322-1.0422", 0, "Herschel");
    }
}
