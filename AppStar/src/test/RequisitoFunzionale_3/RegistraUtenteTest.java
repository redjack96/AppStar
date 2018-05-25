package test.RequisitoFunzionale_3;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static persistence.UtenteDao.inserisciAccount;

public class RegistraUtenteTest {
    private String nome;
    private String cognome;
    private String userID;
    private String password;
    private String email;
    private boolean admin;

    public void setCredenziali(String nome, String cognome, String userID, String password, String email, boolean admin){
        this.nome = nome;
        this.cognome = cognome;
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.admin = admin;
    }

    @Test
    public void inserimentoAccount() throws SQLException{
        System.out.println("\n----------INSERIMENTO DI UN NUOVO AMMINISTRATORE----------\n" +
                "L'amministratore che sta utilizzando l'applicazione ha appena aggiunto i dati di un nuovo \n" +
                "amministratore: {Alberto, Rossi, albertone, albertone, albertone@gmail.com, amministratore}");
        setCredenziali("Alberto", "Rossi", "albertone", "albertone",
                "albertone@gmail.com", true);
        boolean result = inserisciAccount(this.nome, this.cognome, this.userID, this.password, this.email, this.admin);
        Assert.assertEquals(true, result);
        System.err.println("Alberto Rossi È UN NUOVO AMMINISTRATORE.");
    }
}