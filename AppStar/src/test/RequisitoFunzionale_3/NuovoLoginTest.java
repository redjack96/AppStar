package test.RequisitoFunzionale_3;

import control.LoginController;
import entity.UtenteAmministratore;
import entity.UtenteConnesso;
import entity.UtenteRegistrato;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class NuovoLoginTest {
    private ArrayList<String> infoAlberto = new ArrayList<>(6);

    private void setInfoAlberto(){
        //Sono i valori attesi dopo il tentativo di login con le seguenti credenziali: {userID: albertone, pass: albertone}
        this.infoAlberto.add("Alberto"); this.infoAlberto.add("Rossi"); this.infoAlberto.add("albertone"); this.infoAlberto.add("albertone");
        this.infoAlberto.add("albertone@gmail.com"); this.infoAlberto.add("amministratore");
    }

    @Test
    public void adminVerificato(){
        setInfoAlberto();
        System.out.println("\n----------VERIFICA DI LOGIN DA PARTE DI UN AMMINISTRATORE REGISTRATO----------\n" +
                "Alberto è un amministratore e sta provando ad accedere con le sue credenziali : {userID: albertone, pass: albertone}");
        boolean x;
        x = new LoginController().controlloLogin("albertone", "albertone");
        Assert.assertEquals(true, x);
        UtenteRegistrato user = UtenteConnesso.getInstance(null, null);
        UtenteAmministratore admin = new UtenteAmministratore(user.getNome(), user.getCognome(), user.getUserID(),
                user.getPassword(), user.getEmail());
        Assert.assertEquals(this.infoAlberto, admin.getInfo());
    }
}
