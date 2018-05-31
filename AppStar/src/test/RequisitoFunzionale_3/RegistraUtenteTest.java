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

    private void setCredenzialiAlberto(){
        this.nome = "Alberto";
        this.cognome = "Rossi";
        this.userID = "albertone";
        this.password = "albertone";
        this.email = "albertone@gmail.com";
        this.admin = true;
    }

    @Test
    public void inserimentoAccount() throws SQLException{
        System.err.println("\n--PRIMO TEST DEL PACCHETTO Suite, CONFIGURAZIONE DATABASE IN CORSO...--\n");

        System.out.println("\n----------INSERIMENTO DI UN NUOVO AMMINISTRATORE----------\n" +
                "L'amministratore che sta utilizzando l'applicazione ha appena aggiunto i dati di un nuovo \n" +
                "amministratore: {Alberto, Rossi, albertone, albertone, albertone@gmail.com, amministratore}");
        setCredenzialiAlberto();
        boolean result = inserisciAccount(this.nome, this.cognome, this.userID, this.password, this.email, this.admin);
        Assert.assertEquals(true, result);
        System.err.println("Alberto Rossi È UN NUOVO AMMINISTRATORE.");
    }

    @Test
    public void inserimentoAccountFallimentare() throws SQLException{
        System.out.println("\n----------INSERIMENTO DI UN NUOVO UTENTE----------\n" +
                "L'amministratore che sta utilizzando l'applicazione sta tentando di aggiungere un nuovo utente:" +
                " {Alberto, Rossi, al, al, albertone@gmail.com, notAmministratore}");
        setCredenzialiAlberto();
        boolean result = inserisciAccount(this.nome, this.cognome, "al", "al", this.email, this.admin);
        Assert.assertEquals(false, result);
        System.err.println("Alberto Rossi non e' stato registrato in quanto l'ID e la password hanno meno di 6 caratteri.");
    }

}