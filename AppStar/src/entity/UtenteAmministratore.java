package entity;

public class UtenteAmministratore extends UtenteRegistrato {

    public UtenteAmministratore(String nome, String cognome, String userID, String password, String email){
        super(nome, cognome, userID, password, email);
        setAmministratore(true);
    }
}
